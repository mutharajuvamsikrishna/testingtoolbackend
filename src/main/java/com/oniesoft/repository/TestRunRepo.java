package com.oniesoft.repository;

import com.oniesoft.model.TestRun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRunRepo extends JpaRepository<TestRun,Integer> {

    Page<TestRun> findByProjectId(Long projectId, Pageable pageable);
}
