package com.oniesoft.service;

import com.oniesoft.model.TestCase;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TestCaseService {
    TestCase createTestCase(TestCase testCase,long projectId) throws Exception;
    TestCase updateTestCase(TestCase testCase);
    TestCase getTestCaseById(Long id);
    List<TestCase> getAllTestCases();
    void deleteTestCase(Long id);

    Page<TestCase> getAllTestCasesForProject(long projectId,String query, int page, int size);


}
