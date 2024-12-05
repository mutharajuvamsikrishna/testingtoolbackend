package com.oniesoft.service;

import com.oniesoft.model.RunConfig;
import org.springframework.data.domain.Page;

public interface RunConfigService {
    RunConfig addRunConfig(RunConfig runConfig) throws Exception;

    RunConfig updateRunConfig(RunConfig runConfig) throws Exception;

    Page<RunConfig> getAllPageRunConfig(int page, int size);

    RunConfig getRunConfig(int id);

    String deleteRunConfig(int id);
}
