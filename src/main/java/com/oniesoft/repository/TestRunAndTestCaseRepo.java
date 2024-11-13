package com.oniesoft.repository;

import com.oniesoft.model.TestRunAndTestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRunAndTestCaseRepo extends JpaRepository<TestRunAndTestCase,Integer> {

}
