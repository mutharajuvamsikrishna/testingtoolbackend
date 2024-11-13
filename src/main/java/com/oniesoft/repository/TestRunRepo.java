package com.oniesoft.repository;

import com.oniesoft.model.TestRun;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRunRepo extends JpaRepository<TestRun,Integer> {
}
