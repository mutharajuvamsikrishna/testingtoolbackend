package com.oniesoft.service;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestRunResults;

public interface ChartService {


    TestRunResults getAllTestResultsStatus(long projectId);

    TestCaseResults getTestCaseResultsByTRId(int testRunId);
}
