package com.oniesoft.repository;

import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestCase;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRunAndTestCaseRepo extends JpaRepository<TestRunAndTestCase, Integer> {

    @Query("SELECT trtc.testCase FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId")
    List<TestRunAndCase> findTestCasesByTestRunId(@Param("testRunId") int testRunId);

    @Query("SELECT trtc.testCase FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId")
    Page<TestRunAndCase> findTestCasesByTestRunId(@Param("testRunId") int testRunId, Pageable pageable);
    @Query("SELECT trtc.testCase.automationId FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId")
    List<String> findTestCaseIdsByTestRunId(@Param("testRunId") int testRunId);

    @Query("SELECT trtc.testCase FROM TestRunAndTestCase trtc WHERE trtc.testRun.id = :testRunId AND trtc.testCase.automationId = :automationId")
    TestRunAndCase findTestCaseByTestRunIdAndAutomationId(
            @Param("testRunId") int testRunId,
            @Param("automationId") String automationId
    );


    @Modifying
    @Transactional
    @Query("DELETE FROM TestRunAndTestCase trtc WHERE trtc.testCase.id = :id")
    void deleteTestRunAndTestCaseById(Long id);
}
