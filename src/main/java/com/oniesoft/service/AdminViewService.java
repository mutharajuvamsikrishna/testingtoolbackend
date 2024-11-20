package com.oniesoft.service;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;

import java.util.List;

public interface AdminViewService {
    List<Branch> findBranchByCmpId(int id);

    List<Register> findRegisterByBranchId(int id);
}
