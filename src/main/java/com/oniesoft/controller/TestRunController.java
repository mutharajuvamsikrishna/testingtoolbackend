package com.oniesoft.controller;

import com.oniesoft.dto.*;
import com.oniesoft.model.*;

import com.oniesoft.service.TestRunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<TestRun> createTestRun(@RequestBody TestRun testRun) {
        TestRun testRun1 = testRunService.createTestRun(testRun);
        if (testRun1 != null) {
            return ResponseEntity.ok(testRun1);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PostMapping("/addtestrun")
    public ResponseEntity<List<TestRunAndTestCase>> addTestRun(@RequestBody TestRunRequest testRunRequest) {
        List<TestRunAndTestCase> ele = testRunService.addTestRun(testRunRequest);
        if (ele != null) {
            return ResponseEntity.ok(ele);
        } else {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping("/gettestrunbyid")
    public Page<TestRunTableViewDTO> getTestRunByProjectId(@RequestParam Long projectId,@RequestParam String query, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return testRunService.getTestRunById(projectId,query, page, size);
    }

    @GetMapping("/testcases")
    public Page<TestRunAndCase> getTestCasesByTestRunId(@RequestParam int testRunId, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        return testRunService.getPageTestCasesByTestRunId(testRunId, page, size);
    }

    @GetMapping("/listoftestcases")
    public List<TestRunAndCase> getListOfTestCasesByTestRunId(@RequestParam int testRunId) {
        return testRunService.getTestCasesByTestRunId(testRunId);
    }

    @GetMapping("/edittestrun/{testRunId}/{projectId}")
    public ResponseEntity<ApiResponse<EditTestRunTestCasesDTO>> getAllUnMappedTestCases(@PathVariable int testRunId, @PathVariable long projectId, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        EditTestRunTestCasesDTO editTestRunTestCasesDTO = testRunService.getAllUnMappedTestCases(testRunId, projectId, pageable);
        ApiResponse<EditTestRunTestCasesDTO> response = ApiResponse.success(editTestRunTestCasesDTO, editTestRunTestCasesDTO.getPagination());
        return ResponseEntity.ok(response);
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
    public ResponseEntity<?> addTestResults(@RequestBody TestResultDto testResultDto) {
        try {
            TestRunAndCase testRunAndCase = testRunService.testResultsAdd(testResultDto);
            System.out.println("Test case updated: " + testRunAndCase);
            return ResponseEntity.ok(testRunAndCase);
        } catch (Exception e) {
            System.err.println("Failed to update test case: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not Updated: " + e.getMessage());
        }
    }

    @PostMapping("/cloneTestRun/{id}")
    public ResponseEntity<List<TestRunAndTestCase>> cloneTestRun(@PathVariable int id, @RequestParam Long projectId) {
        List<TestRunAndTestCase> ele = testRunService.cloneTestRun(id, projectId);
        if (ele != null) {
            return ResponseEntity.ok(ele);

        } else {
            return ResponseEntity.status(400).body(null);
        }
    }


}
