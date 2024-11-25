package com.oniesoft.serviceimpl;

import com.oniesoft.model.TestResults;
import com.oniesoft.repository.TestResultsRepo;
import com.oniesoft.repository.TestRunAndTestResultsRepo;
import com.oniesoft.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultImpl implements TestResultService {
    @Autowired
    private TestResultsRepo testResultsRepo;
    @Autowired
    private TestRunAndTestResultsRepo testRunAndTestResultsRepo;
    @Override
    public List<TestResults> getTestCasesByTestRunId(int testRunId) {
        return testRunAndTestResultsRepo.findTestResultsByTestRunId(testRunId);
    }
    @Override
    public TestResults gettestResultById(int id){
       return testResultsRepo.findById(id).get();
    }
    @Override
    public List<TestResults> getAlltestResults(){
        return testResultsRepo.findAll();
    }

}
