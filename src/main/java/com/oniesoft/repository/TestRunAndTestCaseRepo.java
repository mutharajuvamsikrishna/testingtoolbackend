package com.oniesoft.repository;

import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRunAndTestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRunAndTestCaseRepo extends JpaRepository<TestRunAndTestCase,Integer> {

    @Query("SELECT trtc.testCase FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId")
    List<TestCase> findTestCasesByTestRunId(@Param("testRunId") int testRunId);
    @Query("SELECT trtc.testCase.id FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId")
    List<Long> findTestCaseIdsByTestRunId(@Param("testRunId") int testRunId);
}
