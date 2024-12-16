package com.oniesoft.repository;

import com.oniesoft.model.Branch;
import com.oniesoft.model.TestRun;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface BranchRepo extends JpaRepository<Branch,Integer> {

    @Query("SELECT b FROM Branch b WHERE "
            + "b.cmpId = :cmpId AND "
            + "(b.branchId LIKE CONCAT('%', :query, '%') "
            + "OR b.branchName LIKE CONCAT('%', :query, '%'))")
    Page<Branch> searchBranchByCmpId(@RequestParam("cmpId") int cmpId, @Param("query") String query, Pageable pageable);

    Branch findByBranchIdAndCmpIdAndBranchName(String branchId, int cmpId, String branchName);

    Page<Branch> findByCmpId(int id, Pageable pageable);
}
