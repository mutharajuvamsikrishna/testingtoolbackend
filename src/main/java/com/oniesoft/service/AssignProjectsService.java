package com.oniesoft.service;

import com.oniesoft.dto.ProjectUserReq;
import com.oniesoft.model.Project;
import com.oniesoft.model.ProjectUsers;

import java.util.List;

public interface AssignProjectsService {

    List<ProjectUsers> assignProjects(ProjectUserReq projectUserReq);

    List<Project> getProjectsId(int registerId);
}
