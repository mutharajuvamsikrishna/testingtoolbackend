package com.oniesoft.service;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminViewService {

    Page<Branch> findBranchByCmpId(int id, int page, int size);


    List<Branch> searchBranch(String query);

    Page<Register> findRegisterByBranchId(int id, int page, int size);
}
