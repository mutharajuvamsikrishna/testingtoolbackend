package com.oniesoft.repository;

import com.oniesoft.model.Branch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch,Integer> {
    Branch findByBranchId(String branchId);


    @Query("SELECT b FROM Branch b WHERE "
            + "b.branchId LIKE CONCAT('%', :query, '%') "
            + "OR b.branchName LIKE CONCAT('%', :query, '%') ")
    List<Branch> searchBranchDetails(@Param("query") String query);

    Branch findByBranchIdAndCmpIdAndBranchName(String branchId, int cmpId, String branchName);

    Page<Branch> findByCmpId(int id, Pageable pageable);
}
