package com.oniesoft.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


import com.oniesoft.model.TestCase;
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
    public Project createProject(Project project) throws Exception {
boolean flag=projectRepository.existsProjectByProjectNameAndBranchId(project.getProjectName(),project.getBranchId());
if(!flag) {
    return projectRepository.save(project);
}else{
    throw new Exception("Project Name Already Exists");
}

    }

    @Override
    public Project updateProject(Project project) throws Exception {
        Optional<Project> projectOptional = projectRepository.findById(project.getId());
        if (projectOptional.isPresent()) {
            Project existingProject = projectOptional.get();

            // Update fields of existingProject with values from input project
            updateAllFields(existingProject, project);

            // Save the updated existingProject
            return projectRepository.save(existingProject);
        } else {
            throw new ResourceNotFoundException("Project Id Not Found: " + project.getId());
        }
    }

    private void updateAllFields(Project existingProject, Project newProject) throws Exception {
        // Update project name with validation
        updateProjectName(existingProject, newProject);

        // Update all other fields
        existingProject.setBranchId(newProject.getBranchId());
        existingProject.setCreatedAt(newProject.getCreatedAt());
        existingProject.setUpdatedAt(LocalDateTime.now()); // Always update the timestamp
        existingProject.setUrl(newProject.getUrl());
        existingProject.setApiBaseURL(newProject.getApiBaseURL());
        existingProject.setBasicAuth(newProject.isBasicAuth());
        existingProject.setBasicAuthUser(newProject.getBasicAuthUser());
        existingProject.setBasicAuthPassword(newProject.getBasicAuthPassword());
        existingProject.setEnableLiveReporting(newProject.isEnableLiveReporting());
        existingProject.setElasticSearchURL(newProject.getElasticSearchURL());
        existingProject.setNotifyTeams(newProject.isNotifyTeams());
        existingProject.setNotifyBlockerCount(newProject.getNotifyBlockerCount());
        existingProject.setNotifyCriticalCount(newProject.getNotifyCriticalCount());
        existingProject.setNotifyMajorCount(newProject.getNotifyMajorCount());
        existingProject.setSendEmailReport(newProject.isSendEmailReport());
        existingProject.setEmailReportTo(newProject.getEmailReportTo());
        existingProject.setJiraUserName(newProject.getJiraUserName());
        existingProject.setJiraPassword(newProject.getJiraPassword());
        existingProject.setJiraURL(newProject.getJiraURL());
        existingProject.setJiraProjectKey(newProject.getJiraProjectKey());

        // You can also handle testCases if needed
        if (newProject.getTestCases() != null) {
            existingProject.setTestCases(newProject.getTestCases());
        }
    }

    private void updateProjectName(Project existingProject, Project newProject) throws Exception {
        if (!existingProject.getProjectName().equals(newProject.getProjectName())) {
            boolean exists = projectRepository.existsProjectByProjectNameAndBranchId(
                    newProject.getProjectName(), newProject.getBranchId());
            if (exists) {
                throw new Exception("Project name is already in use for the given branch.");
            }
            existingProject.setProjectName(newProject.getProjectName());
        }
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
