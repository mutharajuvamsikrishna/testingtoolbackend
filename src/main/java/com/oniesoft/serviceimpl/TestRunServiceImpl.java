package com.oniesoft.serviceimpl;

import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndTestCase;
import com.oniesoft.repository.TestCaseRepository;
import com.oniesoft.repository.TestRunAndTestCaseRepo;
import com.oniesoft.repository.TestRunRepo;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestRunServiceImpl implements TestRunService {
    @Autowired
    private TestRunRepo testRunRepo;
    @Autowired
    private TestRunAndTestCaseRepo testRunAndTestCaseRepo;
    @Autowired
    private TestCaseRepository testCaseRepository;
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

            // Create a new TestRunAndTestCase link
            TestRunAndTestCase link = new TestRunAndTestCase();
            link.setTestRun(testRun);
            link.setTestCase(testCase);
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
    public List<TestCase> getTestCasesByTestRunId(int testRunId) {
        return testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId);
    }
    @Override
    public List<TestCase> getAllUnMappedTestCases(int testRunId) {
        // Get all TestCase IDs that are associated with the given testRunId
        List<Long> testCaseIds = testRunAndTestCaseRepo.findTestCaseIdsByTestRunId(testRunId);

        // Get all TestCase entities from the repository
        List<TestCase> testCases = testCaseRepository.findAll();

        // Filter the TestCase list to exclude those already associated with the testRunId
        List<TestCase> unMappedTestCases = testCases.stream()
                .filter(testCase -> !testCaseIds.contains(testCase.getId()))
                .collect(Collectors.toList());

        // Return the filtered list
        return unMappedTestCases;
    }

}