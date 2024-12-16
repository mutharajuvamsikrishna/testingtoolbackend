package com.oniesoft.repository;

import com.oniesoft.model.Register;
import com.oniesoft.model.TestRun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface TestRunRepo extends JpaRepository<TestRun,Integer> {
    List<TestRun> findByProjectId(Long projectId);
    Page<TestRun> findByProjectId(Long projectId, Pageable pageable);
    Page<TestRun> findByProjectIdAndStatus(Long projectId,String status, Pageable pageable);
    @Query("SELECT tr FROM TestRun tr WHERE "
            + "tr.projectId = :projectId "
            + "AND (tr.testRunName LIKE CONCAT('%', :query, '%') "
            + "OR tr.status LIKE CONCAT('%', :query, '%') "
            + "OR tr.createdBy LIKE CONCAT('%', :query, '%') "
            + "OR tr.startedBy LIKE CONCAT('%', :query, '%'))")
    Page<TestRun> searchTestRunByProjectId(@RequestParam("projectId") Long projectId, @Param("query") String query,Pageable pageable);

}
