package com.oniesoft.service;

import com.oniesoft.dto.*;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TestRunService {

    TestRun createTestRun(TestRun testRun);


//    List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest);

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
    List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest);



    Page<TestRunTableViewDTO> getTestRunById(Long projectId, String query, int page, int size);

    List<TestCaseInRunDTO> getTestCasesByTestRunId(int testRunId);

    Page<TestCaseInRunDTO> getPageTestCasesByTestRunId(int testRunId, int page, int size);

    EditTestRunTestCasesDTO getAllUnMappedTestCases(int testRunId, long projectid, Pageable pageable);


    String integrateTestCasesWithTestingTool(int testRunId,int userId) throws Exception;

    TestRunAndCase testResultsAdd(TestResultDto testResultDto) throws Exception;

    List<TestRunAndTestCase> cloneTestRun(int id, Long projectId, Map<String, String> testRunName);
}
