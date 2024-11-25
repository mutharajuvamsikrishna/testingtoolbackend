package com.oniesoft.repository;

import com.oniesoft.model.TestResults;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestResults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TestRunAndTestResultsRepo extends JpaRepository<TestRunAndTestResults,Integer> {
    @Query("SELECT trtr.testResults FROM TestRunAndTestResults trtr WHERE trtr.testRun.id = :testRunId")
    List<TestResults> findTestResultsByTestRunId(@Param("testRunId") int testRunId);
}
