package com.oniesoft.serviceimpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oniesoft.dto.TestResultDto;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.*;
import com.oniesoft.repository.*;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TestRunServiceImpl implements TestRunService {
    @Autowired
    private TestRunRepo testRunRepo;
    @Autowired
    private TestRunAndTestCaseRepo testRunAndTestCaseRepo;
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    TestRunAndCaseRepo testRunAndCaseRepo;
    @Autowired
   private TestResultsRepo testResultsRepo;

@Autowired
private ProjectRepository projectRepository;

    @Override
    public TestRun createTestRun(TestRun testRun){
        testRun.setCreatedAt(LocalDateTime.now());
        testRun.setUpdatedAt(LocalDateTime.now());

        return testRunRepo.save(testRun);
    }
    @Override
    public List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest) {
        // Fetch the existing TestRun by ID
        TestRun testRun = testRunRepo.findById(testRunRequest.getTestRunId())
                .orElseThrow(() -> new ResourceNotFoundException("TestRun not found with ID: " + testRunRequest.getTestRunId()));
        List<TestRunAndTestCase> ele=new ArrayList<>();
        // Link each TestCase to the existing TestRun
        for (Long testCaseId : testRunRequest.getTestCaseId()) {
            TestCase testCase = testCaseRepository.findById(testCaseId)
                    .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with ID: " + testCaseId));
            TestRunAndCase testRunAndCase=new TestRunAndCase();
            testRunAndCase.setTestCaseName(testCase.getTestCaseName());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
            testRunAndCase.setStatus("new");
            testRunAndCase.setAuthor(testCase.getAuthor());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
             testRunAndCase.setFeature(testCase.getFeature());
            testRunAndCase.setCreatedAt(LocalDateTime.now());
            testRunAndCase.setUpdatedAt(LocalDateTime.now());
           TestRunAndCase testRunAndCase1=testRunAndCaseRepo.save(testRunAndCase);
            TestRunAndTestCase link = new TestRunAndTestCase();
            link.setTestRun(testRun);
            link.setTestCase(testRunAndCase1);
             ele.add(link);
            // Save the link in the TestRunAndTestCase repository
            testRunAndTestCaseRepo.save(link);

        }

        return ele;
    }
    @Override
    public List<TestRun> getTestRunById(Long projectId){

        return testRunRepo.findByProjectId(projectId);
    }
    @Override
    public List<TestRunAndCase> getTestCasesByTestRunId(int testRunId) {
        return testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId);
    }

    @Override
    public List<TestCase> getAllUnMappedTestCases(int testRunId,long projectId) {
        // Get all TestCase IDs that are associated with the given testRunId
        List<String> testCaseIds = testRunAndTestCaseRepo.findTestCaseIdsByTestRunId(testRunId);

        // Get all TestCase entities from the repository
        List<TestCase> testCases = testCaseRepository.findByProject_Id(projectId);

        // Filter the TestCase list to exclude those already associated with the testRunId
        List<TestCase> unMappedTestCases = testCases.stream()
                .filter(testCase -> !testCaseIds.contains(testCase.getAutomationId()))
                .collect(Collectors.toList());

        // Return the filtered list
        return unMappedTestCases;
    }



    @Override
    public String integrateTestCasesWithTestingTool(int testRunId, String ipAddress) throws Exception {
        // Step 1: Fetch TestRun
        Optional<TestRun> testRunOptional = testRunRepo.findById(testRunId);
        if (testRunOptional.isEmpty()) {
            throw new Exception("TestRun ID not found: " + testRunId);
        }

        TestRun testRun = testRunOptional.get();
        testRun.setStartedAt(LocalDateTime.now());

        // Step 2: Fetch Associated Test Cases
        List<TestRunAndCase> testRunAndCases = testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId);
        if (testRunAndCases.isEmpty()) {
            testRun.setStatus("No test cases found");
            testRunRepo.save(testRun);
            return "No test cases found for TestRun ID: " + testRunId;
        }

        // Step 3: Prepare Automation IDs (testCaseIds)
        String automationIds = testRunAndCases.stream()
                .map(testCase -> String.valueOf(testCase.getId()))
                .collect(Collectors.joining(","));

        // Step 4: Fetch Project Details
        Optional<Project> projectOptional = projectRepository.findById(testRun.getProjectId());
        if (projectOptional.isEmpty() || projectOptional.get().getProjectDir() == null) {
            throw new Exception("Project Directory Not Found for project ID: " + testRun.getProjectId());
        }
        String projectPath = projectOptional.get().getProjectDir();
        String mavenPath=projectOptional.get().getMvnPath();
        // Step 5: Send Payload to Windows Service
        String serviceResponse = sendPayloadToWindowsService(testRunId, automationIds, projectPath,mavenPath,ipAddress);

        // Step 6: Determine Status Based on Service Response
        String status;
        if (serviceResponse.contains("Tests executed successfully")) {
            status = "Success";
        } else {
            status = "Fail";
        }

        // Step 7: Update TestRun Status and Save
        testRun.setStatus(status);
        testRunRepo.save(testRun);

        return "Test cases integration " + status + ". Service response: " + serviceResponse;
    }

    private String sendPayloadToWindowsService(int testRunId, String automationIds, String projectPath,String mavenPath,String ipAddress) {
        try {
            // Define the Windows service endpoint
            String windowsServiceUrl = "http://" + ipAddress + ":/run-tests";
System.out.println(mavenPath);
            // Create the payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("testRunId", testRunId);
            payload.put("automationIds", automationIds);
            payload.put("projectPath", projectPath);
            payload.put("mavenPath",mavenPath);
            // Send the payload using an HTTP client
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(windowsServiceUrl))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(payload)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return the response from the service
            return response.body();
        } catch (Exception e) {
            return "Error communicating with Windows service: " + e.getMessage();
        }
    }

    @Override
    public String testResultsAdd(TestResultDto testResultDto) {
        // Fetch TestRunAndCase from repository based on the testRunId and automationId
        TestRunAndCase existingTestRunAndCase = testRunAndTestCaseRepo.findTestCaseByTestRunIdAndAutomationId(testResultDto.getTestRunId(),testResultDto.getAutomationId());

        if (existingTestRunAndCase != null) {
            // Update the status of TestRunAndCase from TestResultDto
            existingTestRunAndCase.setStatus(testResultDto.getStatus());
            TestRunAndCase updatedTestRunAndCase = testRunAndCaseRepo.save(existingTestRunAndCase); // Persist the updated status

            // Create TestResults object and set values from TestResultDto
            TestResults testResults = new TestResults();
            testResults.setTestCaseName(updatedTestRunAndCase.getTestCaseName());
            testResults.setAuthor(updatedTestRunAndCase.getAuthor());
            testResults.setAutomationId(updatedTestRunAndCase.getAutomationId());
            testResults.setFeature(updatedTestRunAndCase.getFeature());
            testResults.setCreatedAt(LocalDateTime.now());
            testResults.setUpdatedAt(LocalDateTime.now());
            testResults.setExcuteTime(testResultDto.getExcuteTime());
            testResults.setStatus(testResultDto.getStatus());
             testResults.setTestRunId(testResultDto.getTestRunId());
            // Save the TestResults entry
            testResultsRepo.save(testResults);

            return "Test result updated successfully for TestRunId: " + testResultDto.getTestRunId();
        } else {
            // Handle the case where no TestRunAndCase is found for the given TestRunId and AutomationId
            return "No TestRunAndCase found for TestRunId: " + testResultDto.getTestRunId() + " and AutomationId: " + testResultDto.getAutomationId();
        }
    }




}