package com.oniesoft.controller;

import com.oniesoft.dto.ProjectDTO;
import com.oniesoft.dto.ProjectUserReq;

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
    public ResponseEntity<List<ProjectUsers>> assignProjectstoUser(@RequestBody ProjectUserReq projectUserReq){
        List<ProjectUsers> ele=  assignProjectsService.assignProjects(projectUserReq);
        if(ele!=null){
            return ResponseEntity.ok(ele);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }
    @PostMapping("/assignuser")
    public ResponseEntity<List<ProjectUsers>> assignUsertoProjects(@RequestBody ProjectUserReq projectUserReq){
        List<ProjectUsers> ele=  assignProjectsService.assignRegisters(projectUserReq);
        if(ele!=null){
            return ResponseEntity.ok(ele);
        }else{
            return ResponseEntity.status(400).body(null);
        }
    }
    @GetMapping("/getassignprojects/{registerId}")
    public List<Project> getProjectsByRegisterId(@PathVariable int registerId) {
        return assignProjectsService.getProjectsId(registerId);
    }

    @GetMapping("/getunmapregister")
    public List<Register> getUnMapRegisters(@RequestParam long projectId,@RequestParam int branchId){
        return assignProjectsService.getAllUnMappedProject(projectId,branchId);
    }
    @GetMapping("/getunmapproject")
    public List<ProjectDTO> getUnMapProjects(@RequestParam int registerId, @RequestParam int branchId){
        return assignProjectsService.getAllUnMappedRegisters(registerId,branchId);
    }

}
