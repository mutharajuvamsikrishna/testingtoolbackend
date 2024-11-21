package com.oniesoft.serviceimpl;

import com.oniesoft.dto.TestResultDto;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.*;
import com.oniesoft.repository.*;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

        // Step 3: Prepare Test Case IDs
        String testCaseIds = testRunAndCases.stream()
                .map(testCase -> String.valueOf(testCase.getId()))
                .collect(Collectors.joining(","));

        // Fetch project details
        Optional<Project> project = projectRepository.findById(testRun.getProjectId());
        if (project.get().getProjectDir()!=null) {
            String mavenProjectPath = project.get().getProjectDir();

            // Step 4: Trigger Maven Tests with testRunId and testCaseIds
            String mavenResponse = triggerMavenTests(testRunId, testCaseIds, mavenProjectPath);

            // Step 5: Determine Status Based on Maven Response
            String status;
            if (mavenResponse.contains("Maven tests executed successfully")) {
                status = "Success";
            } else {
                status = "Fail";
            }

            // Step 6: Update TestRun Status and Save
            testRun.setStatus(status);
            testRunRepo.save(testRun);

            return "Test cases integration " + status + ". Maven test result: " + mavenResponse;
        } else {
            throw new Exception("Project Directory Not Found for project ID: " + testRun.getProjectId());
        }
    }

    private String triggerMavenTests(int testRunId, String testCaseIds, String mavenProjectPath) {
        try {
            // Prepare the Maven command with testRunId and testCaseIds
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("mvn", "test", "-P", "run",
                    "-DtestRunId=" + testRunId,
                    "-DtestCaseIds=" + testCaseIds);

            System.out.println("Running Maven command with testRunId: " + testRunId + " and testCaseIds: " + testCaseIds);
            processBuilder.directory(new File(mavenProjectPath)); // Maven project directory

            // Start the Maven process
            Process process = processBuilder.start();

            // Capture the output of the Maven test execution
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Wait for the Maven process to complete
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return "Maven tests executed successfully:\n" + output;
            } else {
                return "Maven tests failed with exit code " + exitCode + ":\n" + output;
            }
        } catch (Exception e) {
            return "Error running Maven tests: " + e.getMessage();
        }
    }

    @Override
    public String testResultsAdd(List<TestResultDto> testResultDtos) {
        List<TestResults> testResultsList = new ArrayList<>();

        for (TestResultDto testResultDto : testResultDtos) {
            // Fetch TestRunAndCase from repository based on the testRunId
            List<TestRunAndCase> existingTestRunAndCases = testRunAndTestCaseRepo.findTestCasesByTestRunId(testResultDto.getTestRunId());

            if (existingTestRunAndCases != null) {
                // Assuming we are updating all test cases related to the TestRunId
                for (TestRunAndCase existingTestRunAndCase : existingTestRunAndCases) {
                    // Update the status of TestRunAndCase from TestResultDto (status is part of DTO)
                    existingTestRunAndCase.setStatus(testResultDto.getStatus());
         TestRunAndCase testRunAndCase= testRunAndCaseRepo.save(existingTestRunAndCase); // Persist the updated status

                    // Create TestResults object and set values from TestResultDto
                    TestResults testResults = new TestResults();
                    testResults.setTestCaseName(testRunAndCase.getTestCaseName());
                    testResults.setAuthor(testRunAndCase.getAuthor());
                    testResults.setAutomationId(testRunAndCase.getAutomationId());
                    testResults.setFeature(testRunAndCase.getFeature());
                    testResults.setCreatedAt(LocalDateTime.now());  // Can also use `testResultDto.getCreatedAt()`
                    testResults.setUpdatedAt(LocalDateTime.now());  // Set the updated timestamp
                    testResults.setExcuteTime(testResultDto.getExcuteTime());    // Can be customized as needed
                    testResults.setStatus(testResultDto.getStatus());  // Set the status from DTO

                    // Add to the list for bulk saving later
                    testResultsList.add(testResults);
                }
            } else {
                // Handle the case where no TestRunAndCase is found for this TestRunId
                System.err.println("No TestRunAndCase found for TestRunId: " + testResultDto.getTestRunId());
            }
        }

        // Bulk save all TestResults at once to minimize database calls
        if (!testResultsList.isEmpty()) {
            testResultsRepo.saveAll(testResultsList);
        }

        return "Test results updated successfully. " + testResultsList.size() + " entries processed.";
    }



}