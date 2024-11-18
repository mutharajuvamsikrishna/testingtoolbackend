package com.oniesoft.controller;

import com.oniesoft.dto.ProjectUserReq;
import com.oniesoft.dto.TestRunRequest;
import com.oniesoft.model.*;
import com.oniesoft.service.AssignProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectusers/v1")
public class AssignProjectController {
    @Autowired
    private AssignProjectsService assignProjectsService;
    @PostMapping("/assignproject")
    public ResponseEntity<List<ProjectUsers>> addTestRun(@RequestBody ProjectUserReq projectUserReq){
        List<ProjectUsers> ele=  assignProjectsService.assignProjects(projectUserReq);
        if(ele!=null){
            return ResponseEntity.ok(ele);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }
    @GetMapping("/getassignprojects/{registerId}")
    public List<Project> getTestCasesByTestRunId(@PathVariable int registerId) {
        return assignProjectsService.getProjectsId(registerId);
    }
    @GetMapping("/editassignproject/{registerId}")
    public List<Register> getAllUnMappedTestCases(@PathVariable int registerId) {
        return assignProjectsService.getAllUnMappedProject(registerId);
    }
}
