package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;

import com.oniesoft.exception.ResourceNotFoundException;
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
         boolean testCase1=testCaseRepository.existsByProjectIdAndAutomationId(projectId,testCase.getAutomationId());
         if(!testCase1) {
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
    public TestCase updateTestCase(Long projectId, TestCase testCase) throws Exception {
        TestCase existingTestCase = testCaseRepository.findById(testCase.getId())
                .orElseThrow(() -> new ResourceNotFoundException("TestCase not found with ID: " + testCase.getId()));

        // Update basic fields
        existingTestCase.setTestCaseName(testCase.getTestCaseName());
        existingTestCase.setAuthor(testCase.getAuthor());
        existingTestCase.setFeature(testCase.getFeature());
        existingTestCase.setUpdatedAt(LocalDateTime.now());

        // Update Automation ID if needed
        updateAutomationId(existingTestCase, testCase, projectId);

        // Update related TestRunAndCase entries
        List<TestRunAndCase> testRunAndCases = testRunAndCaseRepo.findByTestCaseId(testCase.getId());
        for (TestRunAndCase testRunAndCase : testRunAndCases) {
            testRunAndCase.setTestCaseName(testCase.getTestCaseName());
            testRunAndCase.setAuthor(testCase.getAuthor());
            testRunAndCase.setAutomationId(testCase.getAutomationId());
            testRunAndCase.setFeature(testCase.getFeature());
            testRunAndCase.setUpdatedAt(testCase.getUpdatedAt());
        }

        // Batch save related TestRunAndCase entities
        testRunAndCaseRepo.saveAll(testRunAndCases);

        // Save and return the updated TestCase
        return testCaseRepository.save(existingTestCase);
    }

    private void updateAutomationId(TestCase existingTestCase, TestCase newTestCase, Long projectId) throws Exception {
        if (!existingTestCase.getAutomationId().equals(newTestCase.getAutomationId())) {
            boolean exists = testCaseRepository.existsByProjectIdAndAutomationId(projectId, newTestCase.getAutomationId());
            if (exists) {
                throw new Exception("Automation Id Already In Use");
            }
            existingTestCase.setAutomationId(newTestCase.getAutomationId());
        }
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
    public Page<TestCase> getAllTestCasesForProject(long projectId,String query, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
if(query.equalsIgnoreCase("Initial")) {
    return testCaseRepository.findByProject_Id(projectId, pageable);
}else {
    return testCaseRepository.searchTestCaseDetails(projectId,query,pageable);
}
    }


}
