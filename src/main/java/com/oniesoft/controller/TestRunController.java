package com.oniesoft.controller;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndTestCase;
import com.oniesoft.repository.TestCaseRepository;
import com.oniesoft.repository.TestRunRepo;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<TestCase> getTestCasesByTestRunId(@PathVariable int testRunId) {
        return testRunService.getTestCasesByTestRunId(testRunId);
    }
}
