package com.oniesoft.service;

import com.oniesoft.dto.ProjectDTO;
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


    List<Register> getRegistersByProjectId(long projectId);

    List<Register> getAllUnMappedProject(long projectId, int branchId);

    //    new one
    List<ProjectDTO> getAllUnMappedRegisters(int registerId, int branchId);


    String removeUserFromProject(int registerId, Long projectId);
}
