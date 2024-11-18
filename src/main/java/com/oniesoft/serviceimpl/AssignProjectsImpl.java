package com.oniesoft.serviceimpl;

import com.oniesoft.dto.ProjectUserReq;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.*;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.ProjectUsersRepo;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.service.AssignProjectsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssignProjectsImpl implements AssignProjectsService {
    @Autowired
    private RegisterRepo registerRepo;
    @Autowired
    private ProjectUsersRepo projectUsersRepo;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
    public List<ProjectUsers> assignProjects(ProjectUserReq projectUserReq) {
        // Fetch the existing TestRun by ID
        Project project = projectRepository.findById(projectUserReq.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + projectUserReq.getProjectId()));
        List<ProjectUsers> ele=new ArrayList<>();
        // Link each TestCase to the existing TestRun
        for (int registerId : projectUserReq.getRegisterIds()) {
            Register register = registerRepo.findById(registerId)
                    .orElseThrow(() -> new ResourceNotFoundException("project not found with ID: " + registerId));

            // Create a new TestRunAndTestCase link
            ProjectUsers link = new ProjectUsers();
            link.setProject(project);
            link.setRegister(register);
            ele.add(link);
            // Save the link in the TestRunAndTestCase repository
            projectUsersRepo.save(link);

        }

        return ele;
    }
    @Override
    public List<ProjectUsers> assignRegisters(ProjectUserReq projectUserReq) {
        // Fetch the existing TestRun by ID
        Register register = registerRepo.findById(projectUserReq.getRegisterId())
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + projectUserReq.getRegisterId()));
        List<ProjectUsers> ele=new ArrayList<>();
        // Link each TestCase to the existing TestRun
        for (long projectId : projectUserReq.getProjectIds()) {
          Project project=projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("project not found with ID: " + projectId));

            // Create a new TestRunAndTestCase link
            ProjectUsers link = new ProjectUsers();
            link.setProject(project);
            link.setRegister(register);
            ele.add(link);
            // Save the link in the TestRunAndTestCase repository
            projectUsersRepo.save(link);

        }

        return ele;
    }
    @Override
    public List<ProjectUsers> getAllProjectUsers(){
        return projectUsersRepo.findAll();
    }
@Override
    public List<Project> getProjectsId(int registerId) {
        return projectUsersRepo.findProjectsByRegisterId(registerId);
    }
    @Override
    public List<Register> getAllUnMappedProject(long projectId,int branchId) {
        // Get all TestCase IDs that are associated with the given testRunId
        List<Integer> registerIds = projectUsersRepo.findRegisterIdsByProjectId(projectId);

        // Get all TestCase entities from the repository
        List<Register> registers = registerRepo.findByBranchId(branchId);

        // Filter the TestCase list to exclude those already associated with the testRunId
        List<Register> unMappedRegisters = registers.stream()
                .filter(register -> !registerIds.contains(register.getId()))
                .collect(Collectors.toList());

        // Return the filtered list
        return unMappedRegisters;
    }
//    new one
@Override
public List<Project> getAllUnMappedRegisters(int registerId,int branchId) {
    // Get all TestCase IDs that are associated with the given testRunId
    List<Long> projectIds = projectUsersRepo.findProjectIdsByRegisterId(registerId);

    // Get all TestCase entities from the repository
    List<Project> projects = projectRepository.findByBranchId(branchId);

    // Filter the TestCase list to exclude those already associated with the testRunId
    List<Project> unMappedProjects = projects.stream()
            .filter(project -> !projectIds.contains(project.getId()))
            .collect(Collectors.toList());
    System.out.println(unMappedProjects);
    // Return the filtered list
    return unMappedProjects;
}
}
