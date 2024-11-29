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
    public UserConfig addOrUpdateConfig(UserConfig userConfig){
        Optional<UserConfig> userConfig1=userConfigRepo.findById(userConfig.getId());
        if(userConfig1.isPresent()) {
            return userConfigRepo.save(userConfig);
        }else{
            return userConfigRepo.save(userConfig);
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

}
