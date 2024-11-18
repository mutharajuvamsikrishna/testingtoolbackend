package com.oniesoft.repository;

import com.oniesoft.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch,Integer> {
    Branch findByBranchId(String branchId);

    List<Branch> findByCmpId(int id);
}
