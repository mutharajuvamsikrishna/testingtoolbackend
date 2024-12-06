package com.oniesoft.controller;

import com.oniesoft.dto.ConfigurationDto;
import com.oniesoft.model.RunConfig;
import com.oniesoft.service.RunConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/runconfig/v1")
public class RunConfigController {
    @Autowired
    private RunConfigService runConfigService;


    // Update an existing RunConfig
    @PutMapping("/update")
    public ResponseEntity<?> updateRunConfig(@RequestBody RunConfig runConfig) {
        try {
            RunConfig updatedRunConfig = runConfigService.updateRunConfig(runConfig);
            return ResponseEntity.ok(updatedRunConfig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get paginated list of RunConfigs
    @GetMapping("/list")
    public ResponseEntity<Page<RunConfig>> getAllRunConfigs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<RunConfig> runConfigs = runConfigService.getAllPageRunConfig(page, size);
        return ResponseEntity.ok(runConfigs);
    }

    // Get a single RunConfig by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getRunConfig(@PathVariable int id) {
        try {
            RunConfig runConfig = runConfigService.getRunConfig(id);
            return ResponseEntity.ok(runConfig);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/getrunconfig/{testRunId}")
    public ResponseEntity<?> getRunConfigByTestRunId(@PathVariable int testRunId) {
        try {
            RunConfig runConfig = runConfigService.getRunConfigByTestRunId(testRunId);
            return ResponseEntity.ok(runConfig);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }
    @GetMapping("/getconfigbytestrunid/{testRunId}")
    public ResponseEntity<?> getConfigByTestRunId(@PathVariable int testRunId) {
        try {
            ConfigurationDto configurationDto = runConfigService.getConfiguration(testRunId);
            return ResponseEntity.ok(configurationDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    // Delete a RunConfig by ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRunConfig(@PathVariable int id) {
        try {
            String message = runConfigService.deleteRunConfig(id);
            return ResponseEntity.ok(message);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
