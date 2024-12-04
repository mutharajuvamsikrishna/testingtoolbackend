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



@RestController
@RequestMapping("/projects/v1")
public class ProjectController {
	
	@Autowired
	private ProjectServiceImp projectService;
	
	
	
	@PostMapping("/save")
	public ResponseEntity<Project> createProject(@RequestBody Project project){
		
		Project savedProject=projectService.createProject(project);
		
		return new ResponseEntity<>(savedProject,HttpStatus.OK);
		
	}
   
	@PutMapping("/update")
	public ResponseEntity<Project> updateProject( @RequestBody Project  project) {
		Project updateProject=projectService.updateProject( project);
		return  new ResponseEntity<>(updateProject,HttpStatus.OK) ;
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
