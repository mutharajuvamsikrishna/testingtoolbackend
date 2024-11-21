package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.Project;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.service.ProjectService;

@Service
public class ProjectServiceImp implements ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	@Override
	public Project createProject(Project project) {
      
		project.setCreatedAt(LocalDateTime.now());
		project.setUpdatedAt(LocalDateTime.now());
		return projectRepository.save(project);

	}

	@Override
	public Project updateProject( Project project) {
       
		Project updateProject = projectRepository.findById(project.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project.getId()));
	  updateProject.setProjectName(project.getProjectName());
	  updateProject.setProjectDir(project.getProjectDir());
	  updateProject.setUpdatedAt(LocalDateTime.now());
	  return projectRepository.save(updateProject);

	}

	@Override
	public Project getProject(long id) {

		return projectRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Poject not found with id: "+id));
	}
	@Override
	public List<Project> getProjectsByBranchId(int branchId){
		return projectRepository.findByBranchId(branchId);
	}
	@Override
	public List<Project> getAllProjects() {

		return projectRepository.findAll();
	}

	@Override
	public void deleteProject(long id) {
	   
	    Project toDeleteProject = projectRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

	  
	    projectRepository.deleteById(id);
	}


}
