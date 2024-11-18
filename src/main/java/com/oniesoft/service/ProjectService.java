package com.oniesoft.service;

import java.util.List;

import com.oniesoft.model.Project;

public interface ProjectService {
 
	public Project  createProject(Project project);
	public Project  updateProject(Project project);
	public Project getProject(long id);
	public List<Project> getAllProjects();
	public void deleteProject(long id);
}
