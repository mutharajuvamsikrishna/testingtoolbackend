package com.oniesoft.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oniesoft.model.TestCase;
import com.oniesoft.serviceimpl.TestCaseServiceImpl;

@RestController
@RequestMapping("/testcases/v1")
public class TestCaseController {

    @Autowired
    private TestCaseServiceImpl testCaseService;

    @PostMapping("/save/{projectId}")
    public ResponseEntity<?> saveTestCase(@RequestBody TestCase testCase, @PathVariable long projectId) {
        TestCase savedTestCase = null;
        try {
            savedTestCase = testCaseService.createTestCase(testCase, projectId);
            return ResponseEntity.ok(savedTestCase);
        } catch (Exception e) {
          return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @PutMapping("/update")
    public ResponseEntity<TestCase> updateTestCase( @RequestBody TestCase testCase) {
        TestCase updatedTestCase = testCaseService.updateTestCase(testCase);
        return ResponseEntity.ok(updatedTestCase);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable Long id) {
        TestCase testCase = testCaseService.getTestCaseById(id);
        return ResponseEntity.ok(testCase);
    }
    @GetMapping("/getForProject/{id}")
    public ResponseEntity<List<TestCase>> getTestCaseForProject(@PathVariable Long id) {
        List<TestCase> testCase = testCaseService.getAllTestCasesForProject(id);
        return ResponseEntity.ok(testCase);
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
