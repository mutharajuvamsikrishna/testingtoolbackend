package com.oniesoft.service;

import com.oniesoft.dto.TestResultDto;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestCase;

import java.util.List;

public interface TestRunService {

    TestRun createTestRun(TestRun testRun);


    List<TestRunAndTestCase> addTestRun(TestRunRequest testRunRequest);

    List<TestRun> getTestRunById(Long projectId);

    List<TestRunAndCase> getTestCasesByTestRunId(int testRunId);

    List<TestCase> getAllUnMappedTestCases(int testRunId,long projectid);



    String integrateTestCasesWithTestingTool(int testRunId) throws Exception;

    String testResultsAdd(TestResultDto testResultDto);



}
