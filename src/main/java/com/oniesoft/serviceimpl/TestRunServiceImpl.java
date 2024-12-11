package com.oniesoft.serviceimpl;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.oniesoft.dto.*;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.*;
import com.oniesoft.repository.*;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    private UserConfigRepo userConfigRepo;
    @Autowired
    private RunConfigRepo runConfigRepo;
@Autowired
private FileServiceImpl fileService;
    @Override
    public TestRun createTestRun(TestRun testRun) {
        testRun.setCreatedAt(LocalDateTime.now());
        testRun.setUpdatedAt(LocalDateTime.now());
        testRun.setStatus("New");
        TestRun testRun1 = testRunRepo.save(testRun);
        RunConfig runConfig = new RunConfig();
        runConfig.setTestRunId(testRun1.getId());
        runConfig.setBrowser("chrome");
        runConfig.setHeadLess(false);
        runConfig.setTraceView(false);
        runConfig.setEnableRecording(false);

        runConfig.setTestType("all");
        runConfig.setShortWait(15);
        runConfig.setCustomWait(30);
        runConfig.setRetryCount(0);
        runConfig.setOverrideReport(false);
// JIRA bug reporting
        runConfig.setCreateJiraIssues(false);
        runConfig.setScheduleExecution(false);
        runConfigRepo.save(runConfig);
        return testRun1;
    }

    //    @Override
//    public List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest) {
//        // Fetch the existing TestRun by ID
//        TestRun testRun = testRunRepo.findById(testRunRequest.getTestRunId())
//                .orElseThrow(() -> new ResourceNotFoundException("TestRun not found with ID: " + testRunRequest.getTestRunId()));
//        List<TestRunAndTestCase> ele=new ArrayList<>();
//        // Link each TestCase to the existing TestRun
//        for (Long testCaseId : testRunRequest.getTestCaseId()) {
//            TestCase testCase = testCaseRepository.findById(testCaseId)
//                    .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with ID: " + testCaseId));
//            TestRunAndCase testRunAndCase=new TestRunAndCase();
//            testRunAndCase.setTestCaseName(testCase.getTestCaseName());
//            testRunAndCase.setAutomationId(testCase.getAutomationId());
//            testRunAndCase.setStatus("New");
//            testRunAndCase.setAuthor(testCase.getAuthor());
//            testRunAndCase.setAutomationId(testCase.getAutomationId());
//            testRunAndCase.setTestCaseId(testCase.getId());
//             testRunAndCase.setFeature(testCase.getFeature());
//            testRunAndCase.setCreatedAt(LocalDateTime.now());
//            testRunAndCase.setUpdatedAt(LocalDateTime.now());
//           TestRunAndCase testRunAndCase1=testRunAndCaseRepo.save(testRunAndCase);
//            TestRunAndTestCase link = new TestRunAndTestCase();
//            link.setTestRun(testRun);
//            link.setTestCase(testRunAndCase1);
//             ele.add(link);
//            testRunAndTestCaseRepo.save(link);
//        }
//
//        return ele;
//    }
    @Override
    public List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest) {
        // Fetch the existing TestRun by ID
        TestRun testRun = testRunRepo.findById(testRunRequest.getTestRunId()).orElseThrow(() -> new ResourceNotFoundException("TestRun not found with ID: " + testRunRequest.getTestRunId()));

        // Fetch test case of the run
        List<TestRunAndCase> testCasesByTestRunId = testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunRequest.getTestRunId());

        // add test cases from the request to test run if the number of test cases in the run is 0 (First time adding cases to run)
        List<Long> testCaseIDs = new ArrayList<>();
        if(testCasesByTestRunId.isEmpty()) {
            testCaseIDs = testRunRequest.getTestCaseId().stream().map(id -> testCaseRepository.findByAutomationId(id).getId()).toList();
            testRun.setTestCaseCount(testCaseIDs.size());
        } else {
            // Delete given test case in request from both the repos
            List<Long> ids = testRunRequest.getTestCaseIdsToRemove()
                    .stream().map(autoId -> testCasesByTestRunId.stream().filter(testCase -> testCase.getAutomationId().equals(autoId)).findFirst().get().getId()).toList();
            ids.forEach(testRunAndTestCaseRepo::deleteTestRunAndTestCaseById);
            testRunAndCaseRepo.deleteAllById(ids);
            testCaseIDs = testRunRequest.getTestCaseId().stream().map(id -> testCaseRepository.findByAutomationId(id).getId()).toList();
            testRun.setTestCaseCount(testRun.getTestCaseCount() + testCaseIDs.size() - ids.size());
        }
        testRunRepo.save(testRun);

        List<TestRunAndTestCase> ele = new ArrayList<>();
        // Link each TestCase to the existing TestRun
        for (Long testCaseId : testCaseIDs) {
            TestCase testCase = testCaseRepository.findById(testCaseId).orElseThrow(() -> new ResourceNotFoundException("TestCase not found with ID: " + testCaseId));
            TestRunAndCase testRunAndCase = new TestRunAndCase();
            testRunAndCase.setTestCaseName(testCase.getTestCaseName());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
            testRunAndCase.setStatus("New");
            testRunAndCase.setAuthor(testCase.getAuthor());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
            testRunAndCase.setTestCaseId(testCase.getId());
            testRunAndCase.setFeature(testCase.getFeature());
            testRunAndCase.setCreatedAt(LocalDateTime.now());
            testRunAndCase.setUpdatedAt(LocalDateTime.now());
            TestRunAndCase testRunAndCase1 = testRunAndCaseRepo.save(testRunAndCase);
            TestRunAndTestCase link = new TestRunAndTestCase();
            link.setTestRun(testRun);
            link.setTestCase(testRunAndCase1);
            ele.add(link);
            testRunAndTestCaseRepo.save(link);
        }
        return ele;
    }

    @Override
    public Page<TestRunTableViewDTO> getTestRunById(Long projectId, String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<TestRun> testRuns;
        if(query == null) {
          testRuns = testRunRepo.findByProjectId(projectId, pageable);
        }else{
            testRuns = testRunRepo.findByProjectIdAndStatus(projectId,query,pageable);
        }
        return testRuns.map(testRun -> new TestRunTableViewDTO(testRun.getId(), testRun.getTestRunName(), testRun.getCreatedBy(), testRun.getTestCaseCount(), "TO DO"));
    }

    @Override
    public List<TestRunAndCase> getTestCasesByTestRunId(int testRunId) {
        return testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId);
    }

    @Override
    public Page<TestRunAndCase> getPageTestCasesByTestRunId(int testRunId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId, pageable);
    }

    @Override
    public EditTestRunTestCasesDTO getAllUnMappedTestCases(int testRunId, long projectId, Pageable pageable) {
        // Get all TestCase IDs that are associated with the given testRunId
        List<String> testCaseIds = testRunAndTestCaseRepo.findTestCaseIdsByTestRunId(testRunId);

        // Get all TestCase entities from the repository
        Page<TestCase> testCases = testCaseRepository.findByProject_Id(projectId, pageable);

        // Filter test cases with in a page of test run
        List<String> caseIdsInRun = testCases.getContent().stream().map(TestCase::getAutomationId).filter(testCaseIds::contains).toList();

        // Get Test cases in a page
        List<TestCaseDTO> casesInProject = testCases.getContent().stream().map(testCase ->
            new TestCaseDTO(testCase.getId(), testCase.getTestCaseName(), testCase.getAuthor(), testCase.getAutomationId(), testCase.getFeature(), testCase.getProject())
        ).toList();
        // Set pagination metadata
        ApiResponse.PaginationMetadata pagination = new ApiResponse.PaginationMetadata(
                testCases.getNumber(),
                testCases.getTotalPages(),
                testCases.getTotalElements(),
                testCases.getSize()
        );
        // Return combine data of test cases in a project and test case (automation ids) added to a test run in that page
        return new EditTestRunTestCasesDTO(casesInProject, caseIdsInRun, pagination);
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
        String automationIds = testRunAndCases.stream().map(testCase -> String.valueOf(testCase.getAutomationId())).collect(Collectors.joining(","));

        // Step 4: Fetch Project Details
        Optional<UserConfig> userConfig = userConfigRepo.findByProjectId(testRun.getProjectId());
        if (userConfig.isEmpty() || userConfig.get().getProjectPath() == null) {
            throw new Exception("Project Directory Not Found for project ID: " + testRun.getProjectId());
        }
        String projectPath = userConfig.get().getProjectPath();
        if (userConfig.get().getIpAddress() == null) {
            throw new Exception("Project IpAddress Not Found for project ID: " + testRun.getProjectId());
        }
        String ipAddress = userConfig.get().getIpAddress();
        // Step 5: Send Payload to Windows Service
        String serviceResponse = sendPayloadToWindowsService(testRunId, automationIds, projectPath, ipAddress, testRun.getProjectId());

        return "Test cases integration " + ". Service response: " + serviceResponse;
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
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(windowsServiceUrl)).POST(HttpRequest.BodyPublishers.ofString(new ObjectMapper().writeValueAsString(payload))).build();

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

            TestRunAndCase existingTestRunAndCase = testRunAndTestCaseRepo.findTestCaseByTestRunIdAndAutomationId(testResultDto.getTestRunId(), testResultDto.getAutomationId());

            if (existingTestRunAndCase != null) {
                existingTestRunAndCase.setStatus(testResultDto.getStatus());
                existingTestRunAndCase.setUpdatedAt(LocalDateTime.now());
                existingTestRunAndCase.setExcuteTime(testResultDto.getExcuteTime());
                existingTestRunAndCase.setTraceStack(testResultDto.getTestCaseName());
                String path="";
                try {
                    path=fileService.saveFile(testResultDto.getImage());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
               existingTestRunAndCase.setImage(path);
                TestRunAndCase updatedTestRunAndCase = testRunAndCaseRepo.save(existingTestRunAndCase);
                List<TestRunAndCase> testRunAndCases = testRunAndTestCaseRepo.findTestCasesByTestRunId(testResultDto.getTestRunId());
                boolean allComplete = testRunAndCases.stream()
                        .allMatch(tc -> "Completed".equalsIgnoreCase(tc.getStatus()));
                boolean anyInProgress = testRunAndCases.stream()
                        .anyMatch(tc -> "In Progress".equalsIgnoreCase(tc.getStatus()));

                if (allComplete) {
                    testRun.setStatus("Completed");
                } else if (anyInProgress || "In Progress".equalsIgnoreCase(existingTestRunAndCase.getStatus())) {
                    testRun.setStatus("In Progress");
                } 

                testRun.setUpdatedAt(LocalDateTime.now());
                testRunRepo.save(testRun);

                return updatedTestRunAndCase;
            } else {
                throw new Exception("TestRunAndCase not found for TestRunId: " + testResultDto.getTestRunId() + " and AutomationId: " + testResultDto.getAutomationId());
            }
        } else {
            throw new Exception("TestRun not found for TestRunId: " + testResultDto.getTestRunId());
        }
    }
//    Clone TestRun

    @Override
    public List<TestRunAndTestCase> cloneTestRun(int id, Long projectId) {
        Optional<TestRun> testRunOld = testRunRepo.findById(id);
        List<TestRunAndCase> testCasesByTestRunId = this.getTestCasesByTestRunId(id);
        TestRun testRunNew = new TestRun();
        testRunNew.setTestRunName(testRunOld.get().getTestRunName() + " - Clone");
        testRunNew.setCreatedBy(testRunOld.get().getCreatedBy());
        testRunNew.setProjectId(testRunOld.get().getProjectId());
        TestRun testRun = this.createTestRun(testRunNew);
        List<String> caseIds = testCasesByTestRunId.stream().map(TestRunAndCase::getAutomationId).toList();
        TestRunRequest testRunRequest = new TestRunRequest();
        testRunRequest.setTestRunId(testRun.getId());
        testRunRequest.setTestRunName(testRun.getTestRunName());
        testRunRequest.setTestCaseId(caseIds);
        return this.addTestRun(testRunRequest);
    }
}

