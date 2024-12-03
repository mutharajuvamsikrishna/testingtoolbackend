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
private TestRunAndTestResultsRepo testRunAndTestResultsRepo;
@Autowired
private UserConfigRepo userConfigRepo;


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
            testRunAndCase.setStatus("New");
            testRunAndCase.setAuthor(testCase.getAuthor());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
            testRunAndCase.setTestCaseId(testCase.getId());
             testRunAndCase.setFeature(testCase.getFeature());
            testRunAndCase.setCreatedAt(LocalDateTime.now());
            testRunAndCase.setUpdatedAt(LocalDateTime.now());
           TestRunAndCase testRunAndCase1=testRunAndCaseRepo.save(testRunAndCase);
            TestRunAndTestCase link = new TestRunAndTestCase();
            link.setTestRun(testRun);
            link.setTestCase(testRunAndCase1);
             ele.add(link);
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
       Optional<UserConfig> userConfig = userConfigRepo.findByProjectId(testRun.getProjectId());
        if (userConfig.isEmpty() || userConfig.get().getProjectPath() == null) {
            throw new Exception("Project Directory Not Found for project ID: " + testRun.getProjectId());
        }
        String projectPath = userConfig.get().getProjectPath();
        if (userConfig.get().getIpAddress() == null) {
            throw new Exception("Project IpAddress Not Found for project ID: " + testRun.getProjectId());
        }
  String ipAddress=userConfig.get().getIpAddress();
        // Step 5: Send Payload to Windows Service
        String serviceResponse = sendPayloadToWindowsService(testRunId, automationIds, projectPath,ipAddress,testRun.getProjectId());

        return "Test cases integration "  + ". Service response: " + serviceResponse;
    }

    private String sendPayloadToWindowsService(int testRunId, String automationIds, String projectPath, String ipAddress, Long projectId) {
        String windowsServiceUrl = "http://" + ipAddress + ":3232/run-tests";
        try {
            // Print out the service URL
            System.out.println(windowsServiceUrl);

            // Create the payload
            Map<String, Object> payload = new HashMap<>();

            // Add other fields to the payload
            payload.put("testRunId", testRunId);
            payload.put("automationIds", automationIds);
            payload.put("projectPath", projectPath);

            // Convert Long to String before adding to the payload
            payload.put("projectId", projectId.toString()); // Converts Long to String

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
            // Return an error message if something goes wrong
            return "Error communicating with Windows service : " + " for Windows Service " + windowsServiceUrl + " Exception is " + e;
        }
    }


    @Override
    public TestRunAndCase testResultsAdd(TestResultDto testResultDto) throws Exception {
        Optional<TestRun> testRunOpt = testRunRepo.findById(testResultDto.getTestRunId());

        if (testRunOpt.isPresent()) {
            TestRun testRun = testRunOpt.get();

            TestRunAndCase existingTestRunAndCase = testRunAndTestCaseRepo.findTestCaseByTestRunIdAndAutomationId(
                    testResultDto.getTestRunId(), testResultDto.getAutomationId());

            if (existingTestRunAndCase != null) {
                existingTestRunAndCase.setStatus(testResultDto.getStatus());
                existingTestRunAndCase.setUpdatedAt(LocalDateTime.now());
                TestRunAndCase updatedTestRunAndCase = testRunAndCaseRepo.save(existingTestRunAndCase);

                TestCase testCase=testCaseRepository.findByProjectIdAndAutomationId(testRunOpt.get().getProjectId(), updatedTestRunAndCase.getAutomationId());
                testCase.setUpdatedAt(updatedTestRunAndCase.getUpdatedAt());
                TestResults testResults = new TestResults();
                testResults.setTestCaseName(updatedTestRunAndCase.getTestCaseName());
                testResults.setAuthor(updatedTestRunAndCase.getAuthor());
                testResults.setAutomationId(updatedTestRunAndCase.getAutomationId());
                testResults.setFeature(updatedTestRunAndCase.getFeature());
                testResults.setCreatedAt(LocalDateTime.now());
                testResults.setUpdatedAt(LocalDateTime.now());
                testResults.setExcuteTime(testResultDto.getExcuteTime());
                testResults.setStatus(testResultDto.getStatus());
                TestResults savedTestResults = testResultsRepo.save(testResults);
                TestRunAndTestResults testRunAndTestResults = new TestRunAndTestResults();
                testRunAndTestResults.setTestResults(savedTestResults);
                testRunAndTestResults.setTestRun(testRun);
                testRunAndTestResultsRepo.save(testRunAndTestResults);
                String updateMessage = "Test case " + updatedTestRunAndCase.getAutomationId() +
                        " status updated to " + updatedTestRunAndCase.getStatus();
//                webSocketHelper.broadcast(updateMessage);
                return updatedTestRunAndCase;
            } else {
                throw new Exception("TestRunAndCase not found for TestRunId: " + testResultDto.getTestRunId() +
                        " and AutomationId: " + testResultDto.getAutomationId());
            }
        } else {
            throw new Exception("TestRun not found for TestRunId: " + testResultDto.getTestRunId());
        }
    }
//    Clone TestRun

    @Override
    public List<TestRunAndTestCase> cloneTestRun(int id, Long projectId) {
        TestRun testRunOld = this.getTestRunById(projectId).stream().filter(run -> run.getId() == id).toList().get(0);
        List<TestRunAndCase> testCasesByTestRunId = this.getTestCasesByTestRunId(id);
        TestRun testRunNew =new TestRun();
        testRunNew.setTestRunName(testRunOld.getTestRunName() + " - Clone");
        testRunNew.setCreatedBy(testRunOld.getCreatedBy());
        testRunNew.setProjectId(testRunOld.getProjectId());
        TestRun testRun = this.createTestRun(testRunNew);
        List<Long> caseIds = testCasesByTestRunId.stream().map(TestRunAndCase::getAutomationId).map(autoId -> testCaseRepository.findByAutomationId(autoId).getId()).toList();
        TestRunRequest testRunRequest = new TestRunRequest();
        testRunRequest.setTestRunId(testRun.getId());
        testRunRequest.setTestRunName(testRun.getTestRunName());
        testRunRequest.setTestCaseId(caseIds);
        return this.addTestRun(testRunRequest);
    }
}

