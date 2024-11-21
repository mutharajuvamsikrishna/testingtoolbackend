package com.oniesoft.controller;
import com.oniesoft.dto.TestResultDto;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestCase;

import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testrun/v1")
public class TestRunController {
    @Autowired
    private TestRunService testRunService;
    @PostMapping("/createtestrun")
    public  ResponseEntity<TestRun> createTestRun(@RequestBody TestRun testRun){
        TestRun testRun1=testRunService.createTestRun(testRun);
        if(testRun1!=null){
           return ResponseEntity.ok(testRun1);
        }else{
           return ResponseEntity.status(404).body(null);
        }
    }
    @PostMapping("/addtestrun")
    public ResponseEntity<List<TestRunAndTestCase>> addTestRun(@RequestBody TestRunRequest testRunRequest){
       List<TestRunAndTestCase> ele=  testRunService.addTestRun(testRunRequest);
       if(ele!=null){
           return ResponseEntity.ok(ele);
       }else{
           return ResponseEntity.status(400).body(null);
       }
    }
@GetMapping("/gettestrunbyid")
    public List<TestRun> getTestRunByProjectId(@RequestParam Long projectId){
        return testRunService.getTestRunById(projectId);
}
    @GetMapping("/testcases/{testRunId}")
    public List<TestRunAndCase> getTestCasesByTestRunId(@PathVariable int testRunId) {
        return testRunService.getTestCasesByTestRunId(testRunId);
    }

    @GetMapping("/edittestrun")
    public List<TestCase> getAllUnMappedTestCases(@RequestParam int testRunId,@RequestParam long projectId) {
        return testRunService.getAllUnMappedTestCases(testRunId,projectId);
    }
    @PostMapping("/run/{testRunId}")
    public ResponseEntity<String> runTestCases(@PathVariable int testRunId) {
        try {
            // Call the service method to integrate test cases with the testing tool
            String response = testRunService.integrateTestCasesWithTestingTool(testRunId);
            return ResponseEntity.ok(response); // Return successful response
        } catch (Exception e) {
            // Handle any exceptions by returning an appropriate error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }
    @PutMapping("/addtestresults")
    public ResponseEntity<String> addTestResults(@RequestBody TestResultDto testResultDtos) {
        try {
            // Call the service method to process the test results
            String response = testRunService.testResultsAdd(testResultDtos);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // Handle any unexpected exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing test results: " + e.getMessage());
        }
    }
}
