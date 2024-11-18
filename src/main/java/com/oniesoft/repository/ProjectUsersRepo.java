package com.oniesoft.repository;

import com.oniesoft.model.Project;
import com.oniesoft.model.ProjectUsers;
import com.oniesoft.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProjectUsersRepo extends JpaRepository<ProjectUsers,Integer> {
        @Query("SELECT pur.project FROM ProjectUsers pur WHERE pur.register.id = :registerId")
        List<Project> findProjectsByRegisterId(@Param("registerId") int registerId);
    @Query("SELECT pur.register.id FROM ProjectUsers pur WHERE pur.project.id = :projectId")
    List<Integer> findRegisterIdsByProjectId(@Param("projectId") Long projectId);



}
