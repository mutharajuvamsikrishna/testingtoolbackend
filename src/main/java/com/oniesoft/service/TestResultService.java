package com.oniesoft.service;

import com.oniesoft.model.TestResults;

import java.util.List;

public interface TestResultService {
    List<TestResults> getTestCasesByTestRunId(int testRunId);

    TestResults gettestResultById(int id);

    List<TestResults> getAlltestResults();
}
