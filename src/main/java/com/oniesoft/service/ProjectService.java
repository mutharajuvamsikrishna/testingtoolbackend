package com.oniesoft.service;

import java.util.List;

import com.oniesoft.model.Project;
import org.springframework.data.domain.Page;


public interface ProjectService {
 
	public Project  createProject(Project project);
	public Project  updateProject(Project project);
	public Project getProject(long id);

	List<Project> getProjectsByBranchId(int branchId);

	public List<Project> getAllProjects();


//public Page<Project> getAllPageProject(int page, int size);
//
//	List<Project> searchProject(String query);

    public void deleteProject(long id);
}
