package com.oniesoft.controller;

import java.util.List;

import com.oniesoft.model.Register;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.oniesoft.model.Project;
import com.oniesoft.serviceimpl.ProjectServiceImp;

import lombok.Delegate;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestController
@RequestMapping("/projects/v1")
public class ProjectController {
	
	@Autowired
	private ProjectServiceImp projectService;
	
	
	
	@PostMapping("/save")
	public ResponseEntity<?> createProject(@RequestBody Project project){

		Project savedProject= null;
		try {
			savedProject = projectService.createProject(project);
			return new ResponseEntity<>(savedProject,HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error occurred: " + e.getMessage());
		}
	}
   
	@PutMapping("/update")
	public ResponseEntity<?> updateProject( @RequestBody Project  project) {
		Project updateProject= null;
		try {
			updateProject = projectService.updateProject( project);
			return  new ResponseEntity<>(updateProject,HttpStatus.OK) ;
		} catch (Exception e) {
			return   ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Project Name Already Exists");
		}
	}
	
	@GetMapping("/getProject/{id}")
	public ResponseEntity<Project> getMethodName(@PathVariable long  id) {
		Project getProject=projectService.getProject(id);
		return  ResponseEntity.ok(getProject);
	}
	@GetMapping("/getprojectsbybranchid/{branchId}")
	public List<Project> getProjectsByBranchId(@PathVariable int branchId){
		return projectService.getProjectsByBranchId(branchId);
	}
	@GetMapping("/getAllProjects")
	public ResponseEntity<List<Project>> getAllProjects(){
		
		List<Project> listOfProjects=projectService.getAllProjects();
		
		return ResponseEntity.ok(listOfProjects);
	}
//	@GetMapping("/getpageprojects")
//	public Page<Project> getAllPageDept(@RequestParam(defaultValue = "0") int page,
//										 @RequestParam(defaultValue = "10") int size) {
//		// Call the service method with page and size parameters
//		return projectService.getAllPageProject(page,size);
//	}
//	@GetMapping("/searchproject")
//	public List<Project> searchProjec(@RequestParam String query){
//	return projectService.searchProject(query);
//	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable long id){
		 
		projectService.deleteProject(id);
		
		return ResponseEntity.ok("Project Deleted Successfully");
	}
	
}
