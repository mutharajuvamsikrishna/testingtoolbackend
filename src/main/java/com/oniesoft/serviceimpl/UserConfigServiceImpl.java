package com.oniesoft.serviceimpl;

import com.oniesoft.dto.UserConfigDto;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.Project;
import com.oniesoft.model.UserConfig;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.UserConfigRepo;
import com.oniesoft.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserConfigServiceImpl implements UserConfigService {
    @Autowired
    private UserConfigRepo userConfigRepo;
@Autowired
private ProjectRepository projectRepository;
    @Override
    public UserConfig addOrUpdateConfig(UserConfig userConfig)  {
        UserConfig userConfig1=userConfigRepo.findByUserIdAndProjectId(userConfig.getUserId(),userConfig.getProjectId());
        if(userConfig1==null) {
            return userConfigRepo.save(userConfig);
        }else {
        userConfig1.setProjectPath(userConfig.getProjectPath());
        userConfig1.setIpAddress(userConfig.getIpAddress());
        return userConfigRepo.save(userConfig1);
        }
    }
//    @Override
//    public UserConfig UpdateConfig(UserConfig userConfig) throws Exception {
//        UserConfig userConfig1=userConfigRepo.findByUserIdAndProjectId(userConfig.getUserId(),userConfig.getProjectId());
//        if(userConfig1==null) {
//            return userConfigRepo.save(userConfig);
//        }else {
//            throw new Exception("Project Configuration Already Created");
//        }
//    }
    @Override
    public UserConfig getConfigById(int id) throws Exception {
        Optional<UserConfig> userConfig= userConfigRepo.findById(id);
        if(userConfig.isPresent()){
            return userConfig.get();
        }else{
            throw new Exception("Id Not Find In ConFiguration"+id);
        }
    }
    @Override
    public String deleteConfig(int id){
        userConfigRepo.deleteById(id);
        return "Delete SuccessFully";
    }
    @Override
    public List<UserConfig> getAllConfig(){
      return userConfigRepo.findAll();
    }
//    @Override
//    public List<UserConfig> getAllUserConfigByUserId(int userId){
//        return userConfigRepo.findByUserId(userId);
//    }
@Override
public Page<UserConfigDto> getListOfConfigById(int userId, Pageable pageable) throws Exception {
    Page<UserConfig> userConfigPage = userConfigRepo.findByUserId(userId, pageable);

    if (userConfigPage.isEmpty()) {
        throw new ResourceNotFoundException("No configurations found for User ID: " + userId);
    }

    return userConfigPage.map(userConfig -> {
        Optional<Project> project = projectRepository.findById(userConfig.getProjectId());
        if (project.isEmpty()) {
            throw new ResourceNotFoundException("Project ID not found for configuration: " + userConfig.getProjectId());
        }
        return userConfigResponse(userConfig, project.get());
    });
}
    private UserConfigDto userConfigResponse(UserConfig userConfig,Project project){
        return new UserConfigDto(project.getId(), project.getUrl(),project.getProjectName(),project.isBasicAuth(),project.getBasicAuthUser(),project.getBasicAuthPassword(),project.isEnableLiveReporting(),project.getElasticSearchURL(),project.isNotifyTeams(),
                project.getNotifyBlockerCount(),project.getNotifyCriticalCount(),project.getNotifyMajorCount(),project.isSendEmailReport(),project.getEmailReportTo(),project.getJiraUserName(),project.getJiraPassword(),project.getJiraURL(),
                project.getJiraProjectKey(),userConfig.getIpAddress(),userConfig.getProjectPath());
    }
}
