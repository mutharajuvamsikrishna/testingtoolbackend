package com.oniesoft.repository;

import com.oniesoft.model.TestCase;
import com.oniesoft.model.TestRunAndCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
	
	List<TestCase> findByProject_Id(long projectId);


    TestCase findByProjectIdAndAutomationId(long projectId, String automationId);



}
