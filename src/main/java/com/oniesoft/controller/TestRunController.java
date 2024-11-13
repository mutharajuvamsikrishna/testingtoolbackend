package com.oniesoft.controller;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndTestCase;
import com.oniesoft.repository.TestCaseRepository;
import com.oniesoft.repository.TestRunRepo;
import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TestRunRequest> addTestRun(@RequestBody TestRunRequest testRunRequest){
       TestRunRequest testRunRequest1= testRunService.addTestRun(testRunRequest);
       if(testRunRequest1!=null){
           return ResponseEntity.ok(testRunRequest1);
       }else{
           return ResponseEntity.status(400).body(null);
       }
    }


}
