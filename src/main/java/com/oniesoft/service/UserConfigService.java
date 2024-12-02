package com.oniesoft.service;

import com.oniesoft.model.UserConfig;

import java.util.List;

public interface UserConfigService {
    UserConfig addOrUpdateConfig(UserConfig userConfig) throws Exception;

    UserConfig getConfigById(int id) throws Exception;

    String deleteConfig(int id);

    List<UserConfig> getAllConfig();


    List<UserConfig> getAllUserConfigByUserId(int userId);
}
