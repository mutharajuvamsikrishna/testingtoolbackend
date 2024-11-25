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
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

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
private TestRunAndTestResultsRepo testRunAndTestResultsRepo;
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
    public String integrateTestCasesWithTestingTool(int testRunId) throws Exception {
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
                .map(testCase -> String.valueOf(testCase.getAutomationId()))
                .collect(Collectors.joining(","));

        // Step 4: Fetch Project Details
        Optional<Project> projectOptional = projectRepository.findById(testRun.getProjectId());
        if (projectOptional.isEmpty() || projectOptional.get().getProjectDir() == null) {
            throw new Exception("Project Directory Not Found for project ID: " + testRun.getProjectId());
        }
        String projectPath = projectOptional.get().getProjectDir();
        if (projectOptional.get().getIpAddress() == null) {
            throw new Exception("Project IpAddress Not Found for project ID: " + testRun.getProjectId());
        }
  String ipAddress=projectOptional.get().getIpAddress();
        // Step 5: Send Payload to Windows Service
        String serviceResponse = sendPayloadToWindowsService(testRunId, automationIds, projectPath,ipAddress);

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

    private String sendPayloadToWindowsService(int testRunId, String automationIds, String projectPath,String ipAddress) {
        String windowsServiceUrl="http://" + ipAddress + ":3232/run-tests";
        try {
            // Define the Windows service endpoint
//            String windowsServiceUrl = "http://" + ipAddress + ":3232/run-tests";
            System.out.println(windowsServiceUrl);
            // Create the payload
            Map<String, Object> payload = new HashMap<>();
            payload.put("testRunId", testRunId);
            payload.put("automationIds", automationIds);
            payload.put("projectPath", projectPath);
            // Send the payload using an HTTP client
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(windowsServiceUrl))
                    .POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(payload)))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Return the response from the service
            return response.body();
        } catch (Exception e) {
            return "Error communicating with Windows service : "+" for Windows Service "+windowsServiceUrl +" Exception is "+e;
        }
    }

    @Override
    public TestRunAndCase testResultsAdd(TestResultDto testResultDto, SseEmitter emitter) throws Exception {
        Optional<TestRun> testRun=testRunRepo.findById(testResultDto.getTestRunId());
        TestRunAndCase existingTestRunAndCase = testRunAndTestCaseRepo.findTestCaseByTestRunIdAndAutomationId(testResultDto.getTestRunId(), testResultDto.getAutomationId());

        if (existingTestRunAndCase != null) {
            existingTestRunAndCase.setStatus(testResultDto.getStatus());
            TestRunAndCase updatedTestRunAndCase = testRunAndCaseRepo.save(existingTestRunAndCase);

            TestResults testResults = new TestResults();
            testResults.setTestCaseName(updatedTestRunAndCase.getTestCaseName());
            testResults.setAuthor(updatedTestRunAndCase.getAuthor());
            testResults.setAutomationId(updatedTestRunAndCase.getAutomationId());
            testResults.setFeature(updatedTestRunAndCase.getFeature());
            testResults.setCreatedAt(LocalDateTime.now());
            testResults.setUpdatedAt(LocalDateTime.now());
            testResults.setExcuteTime(testResultDto.getExcuteTime());
            testResults.setStatus(testResultDto.getStatus());
        TestResults testResults1= testResultsRepo.save(testResults);
        TestRunAndTestResults testRunAndTestResults=new TestRunAndTestResults();
         testRunAndTestResults.setTestResults(testResults1);
         testRunAndTestResults.setTestRun(testRun.get());
         testRunAndTestResultsRepo.save(testRunAndTestResults);
            // Send real-time updates via SSE
            if (emitter != null) {
                // Send the updated result to the client
                String updateMessage = "Test case " + updatedTestRunAndCase.getTestCaseName() + " status updated to " + updatedTestRunAndCase.getStatus();
                emitter.send(updateMessage);
            }

            return updatedTestRunAndCase;
        } else {
            if (emitter != null) {
                emitter.send("Error: No TestRunAndCase found for TestRunId: " + testResultDto.getTestRunId() +
                        " and AutomationId: " + testResultDto.getAutomationId());
            }
            throw new Exception("No TestRunAndCase found for TestRunId: " + testResultDto.getTestRunId() +
                    " and AutomationId: " + testResultDto.getAutomationId());
        }
    }


}

