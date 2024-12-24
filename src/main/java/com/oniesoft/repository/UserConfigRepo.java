package com.oniesoft.repository;

import com.oniesoft.model.UserConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserConfigRepo extends JpaRepository<UserConfig,Integer> {

    Optional<UserConfig> findByProjectId(Long projectId);

 

  

    Page<UserConfig> findByUserId(int userId, Pageable pageable);

    void deleteByUserIdAndProjectId(int registerId, Long projectId);
    

    UserConfig findByUserIdAndProjectId(int userId, long projectId);

    Optional<UserConfig> findByProjectIdAndUserId(Long projectId, int userId);
}
