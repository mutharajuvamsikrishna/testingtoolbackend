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

import com.oniesoft.model.Project;
import com.oniesoft.serviceimpl.ProjectServiceImp;

import lombok.Delegate;



@RestController
@RequestMapping("api/projects")
public class ProjectController {
	
	@Autowired
	private ProjectServiceImp projectService;
	
	
	
	@PostMapping("/save")
	public ResponseEntity<Project> createProject(@RequestBody Project project){
		
		Project savedProject=projectService.createProject(project);
		
		return new ResponseEntity<>(savedProject,HttpStatus.OK);
		
	}
   
	@PutMapping("/update/{id}")
	public ResponseEntity<Project> updateProject(@PathVariable long id, @RequestBody Project  project) {
		Project updateProject=projectService.updateProject(id, project);
		return  new ResponseEntity<>(updateProject,HttpStatus.OK) ;
	}
	
	@GetMapping("/getProject/{id}")
	public ResponseEntity<Project> getMethodName(@PathVariable long  id) {
		Project getProject=projectService.getProject(id);
		return  ResponseEntity.ok(getProject);
	}
	
	@GetMapping("/getAllProjects")
	public ResponseEntity<List<Project>> getAllProjects(){
		
		List<Project> listOfProjects=projectService.getAllProjects();
		
		return ResponseEntity.ok(listOfProjects);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteProject(@PathVariable long id){
		 
		projectService.deleteProject(id);
		
		return ResponseEntity.ok("Project Deleted Successfully");
	}
	
}
