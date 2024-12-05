package com.oniesoft.repository;

import com.oniesoft.model.RunConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RunConfigRepo extends JpaRepository<RunConfig,Integer> {


    RunConfig findByTestRunId(int id);
}
