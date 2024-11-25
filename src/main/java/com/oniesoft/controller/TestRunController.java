package com.oniesoft.controller;
import com.oniesoft.dto.TestResultDto;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.*;

import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;

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
    @PostMapping("/run")
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
    @GetMapping("/stream-testcase-updates")
    public SseEmitter streamTestCaseUpdates() {
        SseEmitter emitter = new SseEmitter();

        // Run a background task to simulate sending updates
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 0; i < 10; i++) { // Replace this with actual test case update logic
                    String update = "Update for test case " + i;
                    emitter.send(update); // Send each update to the client
                    Thread.sleep(1000); // Simulate delay
                }
                emitter.complete(); // Complete the SSE stream when done
            } catch (Exception e) {
                emitter.completeWithError(e); // Handle errors
            }
        });

        return emitter;
    }


    @PutMapping("/addtestresults")
    public SseEmitter addTestResults(@RequestBody TestResultDto testResultDto) {
        SseEmitter emitter = new SseEmitter();

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                // Call the service method to process the test results with SSE
                testRunService.testResultsAdd(testResultDto, emitter);
                emitter.complete();
            } catch (Exception e) {
                try {
                    emitter.send("An error occurred: " + e.getMessage());
                } catch (IOException ioException) {
                    // Log the error
                }
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }

}
