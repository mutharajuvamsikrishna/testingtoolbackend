package com.oniesoft.serviceimpl;

import com.oniesoft.model.UserConfig;
import com.oniesoft.repository.UserConfigRepo;
import com.oniesoft.service.UserConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class UserConfigServiceImpl implements UserConfigService {
    @Autowired
    private UserConfigRepo userConfigRepo;

    @Override
    public UserConfig addOrUpdateConfig(UserConfig userConfig) throws Exception {
        UserConfig userConfig1=userConfigRepo.findByUserIdAndProjectId(userConfig.getUserId(),userConfig.getProjectId());
        if(userConfig1==null) {
            return userConfigRepo.save(userConfig);
        }else {
            throw new Exception("Project Configuration Already Created");
        }
    }

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
    @Override
    public List<UserConfig> getAllUserConfigByUserId(int userId){
        return userConfigRepo.findByUserId(userId);
    }
}
