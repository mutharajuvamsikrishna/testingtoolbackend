package com.oniesoft.service;

import com.oniesoft.model.TestCase;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TestCaseService {
    TestCase createTestCase(TestCase testCase,long projectId) throws Exception;



    @Transactional
    TestCase updateTestCase(Long projectId, TestCase testCase) throws Exception;

    TestCase getTestCaseById(Long id);
    List<TestCase> getAllTestCases();
    void deleteTestCase(Long id);

    Page<TestCase> getAllTestCasesForProject(long projectId,String query, int page, int size);


}
