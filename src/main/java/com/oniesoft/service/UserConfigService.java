package com.oniesoft.service;

import com.oniesoft.dto.UserConfigDto;
import com.oniesoft.model.UserConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserConfigService {
    UserConfig addOrUpdateConfig(UserConfig userConfig);

    UserConfig getConfigById(int id) throws Exception;

    String deleteConfig(int id);

    List<UserConfig> getAllConfig();


    //    @Override
    //    public List<UserConfig> getAllUserConfigByUserId(int userId){
    //        return userConfigRepo.findByUserId(userId);
    //    }
    Page<UserConfigDto> getListOfConfigById(int userId, Pageable pageable) throws Exception;
}
