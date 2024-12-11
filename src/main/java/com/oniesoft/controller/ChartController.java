package com.oniesoft.controller;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestRunResults;
import com.oniesoft.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chart/v1/")
public class ChartController {
    @Autowired
    private ChartService chartService;
    @GetMapping("gettestrunresultsbyprojectid/{projectId}")
    public TestRunResults getTestResultsByProjectId(@PathVariable Long projectId){
        return chartService.getAllTestResultsStatus(projectId);
    }
@GetMapping("gettestcaseresults/{testRunId}")
    public TestCaseResults getTestCaseResultsByTRId(@PathVariable int testRunId){
        return chartService.getTestCaseResultsByTRId(testRunId);
}
}
