package com.oniesoft.service;

import com.oniesoft.model.RunConfig;
import org.springframework.data.domain.Page;

public interface RunConfigService {


    RunConfig updateRunConfig(RunConfig runConfig) throws Exception;

    Page<RunConfig> getAllPageRunConfig(int page, int size);

    RunConfig getRunConfig(int id);

    RunConfig getRunConfigByTestRunId(int testRunId);

    String deleteRunConfig(int id);
}
