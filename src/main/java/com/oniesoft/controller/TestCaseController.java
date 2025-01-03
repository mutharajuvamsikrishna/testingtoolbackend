package com.oniesoft.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oniesoft.model.TestCase;
import com.oniesoft.serviceimpl.TestCaseServiceImpl;

@RestController
@RequestMapping("/testcases/v1")
public class TestCaseController {

    @Autowired
    private TestCaseServiceImpl testCaseService;

    @PostMapping("/save/{projectId}")
    public ResponseEntity<?> saveTestCase(@RequestBody TestCase testCase, @PathVariable long projectId) {
        TestCase savedTestCase;
        try {
            savedTestCase = testCaseService.createTestCase(testCase, projectId);
            return ResponseEntity.ok(savedTestCase);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/update/{projectId}")
    public ResponseEntity<?> updateTestCase( @PathVariable Long projectId,@RequestBody TestCase testCase){

        TestCase savedTestCase;
        try {
            savedTestCase = testCaseService.updateTestCase(projectId,testCase);
            return ResponseEntity.ok(savedTestCase);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable Long id) {
        TestCase testCase = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCase);
    }

    @GetMapping("/getForProject")
    public ResponseEntity<Page<TestCase>> getTestCaseForProject(
            @RequestParam Long id,
            @RequestParam(defaultValue = "Initial") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<TestCase> testCases = testCaseService.getAllTestCasesForProject(id,query, page, size);
        return ResponseEntity.ok(testCases);
    }



    @GetMapping("/getAll")
    public ResponseEntity<List<TestCase>> getAllTestCases() {
        List<TestCase> testCases = testCaseService.getAllTestCases();
        return ResponseEntity.ok(testCases);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTestCase(@PathVariable Long id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.ok("TestCase deleted successfully");
    }
}
