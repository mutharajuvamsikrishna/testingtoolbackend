package com.oniesoft.service;

import com.oniesoft.model.Branch;

import java.util.List;

public interface BranchService {
    Branch addBranch(Branch branch) throws Exception;

    Branch updateBranch(Branch branch) throws Exception;

    Branch getBranchById(int branchId) throws Exception;

    List<Branch> getAllBranches();
}
