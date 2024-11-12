package com.oniesoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oniesoft.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
