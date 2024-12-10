package com.oniesoft.service;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestRunResults;

public interface ChartService {
    TestRunResults getAllTestResultsStatus();

    TestCaseResults getTestCaseResultsByTRId(int testRunId);
}
