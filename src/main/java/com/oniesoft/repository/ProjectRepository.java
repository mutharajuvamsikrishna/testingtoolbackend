package com.oniesoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oniesoft.model.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {


    List<Project> findByBranchId(int branchId);
}
