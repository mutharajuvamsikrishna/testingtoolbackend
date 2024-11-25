package com.oniesoft.controller;

import com.oniesoft.model.TestResults;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.service.TestResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/testresults/v1")
public class TestResultsController {
    @Autowired
    private TestResultService testResultService;
    @GetMapping("/testresults/{testRunId}")
    public List<TestResults> getTestCasesByTestRunId(@PathVariable int testRunId) {
        return testResultService.getTestCasesByTestRunId(testRunId);
    }
    @GetMapping("/testresultbyid/{testResultId}")
    public TestResults getTestResultsById(@PathVariable int testResultsId){
        return testResultService.gettestResultById(testResultsId);
    }
    @GetMapping("/getalltestresults")
    public List<TestResults> getAllTestResults(){
        return testResultService.getAlltestResults();
    }
}
