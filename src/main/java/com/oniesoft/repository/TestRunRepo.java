package com.oniesoft.repository;

import com.oniesoft.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRunRepo extends JpaRepository<TestRun,Integer> {
    List<TestRun> findByProjectId(Long projectId);
}
