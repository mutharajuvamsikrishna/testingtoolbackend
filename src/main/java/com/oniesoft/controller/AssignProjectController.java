package com.oniesoft.controller;

import com.oniesoft.dto.ProjectDTO;
import com.oniesoft.dto.ProjectUserReq;

import com.oniesoft.model.*;
import com.oniesoft.service.AssignProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projectusers/v1")
public class AssignProjectController {
    @Autowired
    private AssignProjectsService assignProjectsService;
    @PostMapping("/assignproject")
    public ResponseEntity<?> assignProjectstoUser(@RequestBody ProjectUserReq projectUserReq) {
        try {
            // Call the service method to assign projects
            List<ProjectUsers> ele = assignProjectsService.assignProjects(projectUserReq);

            // Return success response
            return ResponseEntity.ok(ele);
        } catch (Exception e) {
            // Log the exception for debugging
            e.printStackTrace();

            // Return a meaningful error response
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/assignuser")
    public ResponseEntity<?> assignUsertoProjects(@RequestBody ProjectUserReq projectUserReq){
        List<ProjectUsers> ele=  assignProjectsService.assignRegisters(projectUserReq);
        try {
            return ResponseEntity.ok(ele);
        }catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }

    }
    @GetMapping("/getassignprojects/{registerId}")
    public List<Project> getProjectsByRegisterId(@PathVariable int registerId) {
        return assignProjectsService.getProjectsId(registerId);
    }
    @GetMapping("/getassignregisters/{projectId}")
    public List<Register> getRegistersByProjectId(@PathVariable Long projectId) {
        return assignProjectsService.getRegistersByProjectId(projectId);
    }
    @GetMapping("/getunmapregister")
    public List<Register> getUnMapRegisters(@RequestParam long projectId,@RequestParam int branchId){
        return assignProjectsService.getAllUnMappedProject(projectId,branchId);
    }
    @GetMapping("/getunmapproject")
    public List<ProjectDTO> getUnMapProjects(@RequestParam int registerId, @RequestParam int branchId){
        return assignProjectsService.getAllUnMappedRegisters(registerId,branchId);
    }
@DeleteMapping("/unassign")
    public ResponseEntity<?> deleteProjectByRegId(@RequestParam long projectId,int registerId){
  String msg = assignProjectsService.removeUserFromProject(registerId,projectId);
  if(msg.isEmpty()){
     return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
  }else{
      return ResponseEntity.ok(msg);
  }
}
}
