package com.oniesoft.serviceimpl;

import com.oniesoft.model.RunConfig;
import com.oniesoft.repository.RunConfigRepo;
import com.oniesoft.service.RunConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RunConfigServiceImpl implements RunConfigService {
    @Autowired
    private RunConfigRepo runConfigRepo;
    @Override
    public RunConfig addRunConfig(RunConfig runConfig) throws Exception {
        Optional<RunConfig> runConfig1=runConfigRepo.findByTestRunId(runConfig.getTestRunId());
        if(runConfig1.isEmpty()){
         return  runConfigRepo.save(runConfig);
        }else {
            throw new Exception("RunConfig Already Exist for "+runConfig.getTestRunId());
        }
    }
    @Override
    public RunConfig updateRunConfig(RunConfig runConfig) throws Exception {
     Optional<RunConfig> runConfig1=runConfigRepo.findById(runConfig.getId());
     if(runConfig1.isPresent()){
       return   runConfigRepo.save(runConfig);
     }else{
         throw new Exception("We Didn't Find RunConfig ID of "+runConfig.getId());
     }
    }
    @Override
    public Page<RunConfig> getAllPageRunConfig(int page,int size){
        Pageable pageable = PageRequest.of(page, size);
        return runConfigRepo.findAll(pageable);
    }
    @Override
    public RunConfig getRunConfig(int id){
        return runConfigRepo.findById(id).get();
    }
    @Override
    public String deleteRunConfig(int id){
        runConfigRepo.deleteById(id);
        return "deleted SuccessFully";
    }
}
