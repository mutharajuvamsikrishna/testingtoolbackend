package com.oniesoft.service;

import com.oniesoft.model.TestCase;

import java.util.List;

public interface TestCaseService {
    TestCase createTestCase(TestCase testCase,long projectId);
    TestCase updateTestCase(Long id, TestCase testCase);
    TestCase getTestCaseById(Long id);
    List<TestCase> getAllTestCases();
    void deleteTestCase(Long id);
}
