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
    public TestRunRequest addTestRun(TestRunRequest testRunRequest) {
        // Fetch the existing TestRun by ID
        TestRun testRun = testRunRepo.findById(testRunRequest.getTestRunId())
                .orElseThrow(() -> new ResourceNotFoundException("TestRun not found with ID: " + testRunRequest.getTestRunId()));

        // Link each TestCase to the existing TestRun
        for (Long testCaseId : testRunRequest.getTestCaseId()) {
            TestCase testCase = testCaseRepository.findById(testCaseId)
                    .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with ID: " + testCaseId));

            // Create a new TestRunAndTestCase link
            TestRunAndTestCase link = new TestRunAndTestCase();
            link.setTestRun(testRun);
            link.setTestCase(testCase);

            // Save the link in the TestRunAndTestCase repository
            testRunAndTestCaseRepo.save(link);

            System.out.println("Linked TestCase: " + testCase.getTestCaseName() + " with TestRun: " + testRun.getTestRunName());
        }

        return testRunRequest;
    }

}