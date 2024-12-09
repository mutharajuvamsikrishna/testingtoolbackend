package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.repository.TestRunAndCaseRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private TestRunAndCaseRepo testRunAndCaseRepo;
    @Override
    public TestCase createTestCase(TestCase testCase,long projectId) throws Exception {
    	 Project project = projectRepository.findById(projectId)
                 .orElseThrow(() -> new RuntimeException("Project not found with id: " + projectId));
         TestCase testCase1=testCaseRepository.findByProjectIdAndAutomationId(projectId,testCase.getAutomationId());
         if(testCase1==null) {
             testCase.setProject(project);
             testCase.setCreatedAt(LocalDateTime.now());
             testCase.setUpdatedAt(LocalDateTime.now());
             testCase.setProject(project);

             return testCaseRepository.save(testCase);
         }else{
             throw new Exception("Automation Id is Already Exists");
         }
    }

    @Override
    @Transactional
    public TestCase updateTestCase(TestCase testCase) {
        TestCase existingTestCase = testCaseRepository.findById(testCase.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "TestCase not found"));
        existingTestCase.setTestCaseName(testCase.getTestCaseName());
        existingTestCase.setAuthor(testCase.getAuthor());
        existingTestCase.setAutomationId(testCase.getAutomationId());
        existingTestCase.setFeature(testCase.getFeature());
        existingTestCase.setUpdatedAt(LocalDateTime.now());
        List<TestRunAndCase> testRunAndCases = new ArrayList<>();
        List<TestRunAndCase> testRunAndCase1=testRunAndCaseRepo.findByTestCaseId(testCase.getId());
        for(TestRunAndCase testRunAndCase2:testRunAndCase1){
            testRunAndCase2.setTestCaseName(testCase.getTestCaseName());
            testRunAndCase2.setAuthor(testCase.getAuthor());
            testRunAndCase2.setAutomationId(testCase.getAutomationId());
            testRunAndCase2.setFeature(testCase.getFeature());
            testRunAndCase2.setUpdatedAt(testCase.getUpdatedAt());
            testRunAndCases.add(testRunAndCase2);
        }
        testRunAndCaseRepo.saveAll(testRunAndCases);
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

	@Override
    public Page<TestCase> getAllTestCasesForProject(long projectId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        return testCaseRepository.findByProject_Id(projectId, pageable);
    }
    @Override
    public List<TestCase> searchTestCases(String query){
        return testCaseRepository.searchTestCaseDetails(query);
    }

}
