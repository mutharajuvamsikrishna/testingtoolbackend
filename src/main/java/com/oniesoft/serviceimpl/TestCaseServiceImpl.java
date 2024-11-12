package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.oniesoft.model.Project;
import com.oniesoft.model.TestCase;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.TestCaseRepository;
import com.oniesoft.service.TestCaseService;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public TestCase createTestCase(TestCase testCase,long projectId) {
    	 Project project = projectRepository.findById(projectId)
                 .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
testCase.setProject(project);
        testCase.setCreatedAt(LocalDateTime.now());
        testCase.setUpdatedAt(LocalDateTime.now());
        testCase.setProject(project);
        testCase.setStatus("New");
        return testCaseRepository.save(testCase);
    }

    @Override
    public TestCase updateTestCase(Long id, TestCase testCase) {
        TestCase existingTestCase = testCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TestCase not found"));
        existingTestCase.setTestCaseName(testCase.getTestCaseName());
        existingTestCase.setStatus(testCase.getStatus());
        existingTestCase.setAuthor(testCase.getAuthor());
        existingTestCase.setAutomationId(testCase.getAutomationId());
        existingTestCase.setFeature(testCase.getFeature());
        existingTestCase.setUpdatedAt(LocalDateTime.now());
        return testCaseRepository.save(existingTestCase);
    }

    @Override
    public TestCase getTestCaseById(Long id) {
        return testCaseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TestCase not found"));
    }

    @Override
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public void deleteTestCase(Long id) {
        if (!testCaseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "TestCase not found");
        }
        testCaseRepository.deleteById(id);
    }
}
