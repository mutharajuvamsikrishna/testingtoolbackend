package com.oniesoft.repository;

import com.oniesoft.model.TestResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestResultsRepo extends JpaRepository<TestResults,Integer> {
}
