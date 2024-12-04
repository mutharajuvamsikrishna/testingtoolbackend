package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        project.setBasicAuth(false);
        project.setHeadLess(false);

        project.setTraceView(true);

        project.setEnableRecording(true);

        project.setShortWait(0);

        project.setCustomWait(0);

        project.setRetryCount(0);
        int retryCount;

        // REPORT CONFIGURATIONS

        project.setEnableLiveReporting(true);


        project.setOverrideReport(false);
        boolean overrideReport;

        // TEAMS NOTIFICATION

        project.setNotifyTeams(false);
        boolean notifyTeams;

        project.setNotifyBlockerCount(0);

        project.setNotifyCriticalCount(0);

        project.setNotifyMajorCount(0);

        // EMAIL NOTIFICATION

        project.setSendEmailReport(false);


        // JIRA CONFIGURATIONS

        project.setCreateJiraIssues(false);

        return projectRepository.save(project);

    }

    @Override
    public Project updateProject(Project project) {

        Project updateProject = projectRepository.findById(project.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project not found with id: " + project.getId()));
        project.setUpdatedAt(updateProject.getUpdatedAt());
        return projectRepository.save(project);

    }

    @Override
    public Project getProject(long id) {

        return projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Poject not found with id: " + id));
    }

    @Override
    public List<Project> getProjectsByBranchId(int branchId) {
        return projectRepository.findByBranchId(branchId);
    }

    @Override
    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }
//    @Override
//    public Page<Project> getAllPageProject(int page, int size) {
//        // Create a Pageable object with a dynamic page number and page size
//        Pageable pageable = PageRequest.of(page, size);
//
//        // Fetch paginated departments
//        return projectRepository.findAll(pageable);
//    }
//@Override
//public List<Project> searchProject(String query){
//        return projectRepository.searchProjectDetails(query);
//}
    @Override
    public void deleteProject(long id) {

        Project toDeleteProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));


        projectRepository.deleteById(id);
    }


}
