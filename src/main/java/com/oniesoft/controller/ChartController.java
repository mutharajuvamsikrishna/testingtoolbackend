package com.oniesoft.controller;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestCasesSummaryDTO;
import com.oniesoft.dto.TestRunResults;
import com.oniesoft.service.ChartService;
import com.oniesoft.service.S3Service;
import com.oniesoft.serviceimpl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chart/v1/")
public class ChartController {
    @Autowired
    private ChartService chartService;
    @Autowired
    private S3Service s3Service;
    @Autowired
    private FileServiceImpl fileServiceImple;

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
    public ResponseEntity<Resource> downloadFile(@RequestParam("key") String key) {
      return fileServiceImple.getFilePath(key);
    }
}
