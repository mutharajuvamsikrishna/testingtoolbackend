package com.oniesoft.repository;

import com.oniesoft.model.TestRunAndCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TestRunAndCaseRepo extends JpaRepository<TestRunAndCase,Long> {


    List<TestRunAndCase> findByTestCaseId(long id);
}
