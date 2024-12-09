package com.oniesoft.repository;

import com.oniesoft.model.RunConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface RunConfigRepo extends JpaRepository<RunConfig,Integer> {


    RunConfig findByTestRunId(int id);



    List<RunConfig> findByScheduleExecutionTrue();
}
