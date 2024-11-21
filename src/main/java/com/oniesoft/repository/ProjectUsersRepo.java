package com.oniesoft.repository;

import com.oniesoft.model.Project;
import com.oniesoft.model.ProjectUsers;
import com.oniesoft.model.TestCase;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Query("SELECT pur.project.id FROM ProjectUsers pur WHERE pur.register.id = :registerId")
    List<Long> findProjectIdsByRegisterId(@Param("registerId") int registerId);
    @Modifying
    @Transactional
    @Query("DELETE FROM ProjectUsers pur WHERE pur.register.id = :registerId AND pur.project.id = :projectId")
    void deleteByRegisterIdAndProjectId(@Param("registerId") int registerId, @Param("projectId") Long projectId);
}
