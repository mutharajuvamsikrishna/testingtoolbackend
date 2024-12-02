package com.oniesoft.repository;

import com.oniesoft.model.UserConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserConfigRepo extends JpaRepository<UserConfig,Integer> {

    Optional<UserConfig> findByProjectId(Long projectId);

    List<UserConfig> findByUserId(int userId);
}
