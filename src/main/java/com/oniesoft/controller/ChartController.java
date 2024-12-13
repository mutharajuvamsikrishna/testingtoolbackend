package com.oniesoft.controller;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestCasesSummaryDTO;
import com.oniesoft.dto.TestRunResults;
import com.oniesoft.service.ChartService;
import com.oniesoft.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chart/v1/")
public class ChartController {
    @Autowired
    private ChartService chartService;
    @Autowired
    private S3Service s3Service;

    @GetMapping("gettestrunresultsbyprojectid/{projectId}")
    public TestRunResults getTestResultsByProjectId(@PathVariable Long projectId) {
        return chartService.getAllTestResultsStatus(projectId);
    }

    @GetMapping("gettestcaseresults/{testRunId}")
    public TestCaseResults getTestCaseResultsByTRId(@PathVariable int testRunId) {
        return chartService.getTestCaseResultsByTRId(testRunId);
    }

    @GetMapping("getTestCaseSummary/{testCaseId}")
    public TestCasesSummaryDTO getTestCaseSummaryFromAllRuns(@PathVariable Long testCaseId) {
        return chartService.getTestCaseSummaryFromAllRuns(testCaseId);
    }

    // Retrieve a pre-signed URL for an image
    @GetMapping("/download")
    public ResponseEntity<String> downloadFile(@RequestParam("key") String key) {
        try {
            String url = s3Service.getPresignedUrl(key);
            return ResponseEntity.ok(url);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
