package com.oniesoft.repository;

import com.oniesoft.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;

import com.oniesoft.model.Project;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByBranchId(int branchId);
//    @Query("SELECT p FROM Project p WHERE "
//            + "p.projectName LIKE CONCAT('%', :query, '%') ")
//    List<Project> searchProjectDetails(@Param("query") String query);
}
