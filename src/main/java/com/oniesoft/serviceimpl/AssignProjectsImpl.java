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
        Register register = registerRepo.findById(projectUserReq.getRegisterId())
                .orElseThrow(() -> new ResourceNotFoundException("Register not found with ID: " + projectUserReq.getRegisterId()));
        List<ProjectUsers> ele=new ArrayList<>();
        // Link each TestCase to the existing TestRun
        for (Long projectId : projectUserReq.getProjectIds()) {
            Project project = projectRepository.findById(projectId)
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
    public List<Project> getProjectsId(int registerId) {
        return projectUsersRepo.findProjectsByRegisterId(registerId);
    }
}
