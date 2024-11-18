package com.oniesoft.service;

import com.oniesoft.dto.ProjectUserReq;
import com.oniesoft.model.Project;
import com.oniesoft.model.ProjectUsers;
import com.oniesoft.model.Register;

import java.util.List;

public interface AssignProjectsService {

    List<ProjectUsers> assignProjects(ProjectUserReq projectUserReq);

    List<ProjectUsers> assignRegisters(ProjectUserReq projectUserReq);

    List<ProjectUsers> getAllProjectUsers();

    List<Project> getProjectsId(int registerId);


    List<Project> getProjectsByBranchId(int branchId);

    List<Register> getAllUnMappedProject(long projectId, int branchId);

    //    new one
    List<Project> getAllUnMappedRegisters(int registerId,int branchId);
}
