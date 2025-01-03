package com.oniesoft.repository;

import com.oniesoft.model.Register;
import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRunAndCase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
	

    Page<TestCase> findByProject_Id(long projectId, Pageable pageable);

    TestCase findByProjectIdAndAutomationId(long projectId, String automationId);
    boolean existsByProjectIdAndAutomationId(long projectId, String automationId);

    @Query("SELECT t FROM TestCase t WHERE "
            + "t.project.id = :projectId AND "
            + "(t.testCaseName LIKE CONCAT('%', :query, '%') "
            + "OR t.author LIKE CONCAT('%', :query, '%') "
            + "OR t.automationId LIKE CONCAT('%', :query, '%') "
            + "OR t.feature LIKE CONCAT('%', :query, '%'))")
    Page<TestCase> searchTestCaseDetails(@Param("projectId") Long projectId, @Param("query") String query, Pageable pageable);


}
