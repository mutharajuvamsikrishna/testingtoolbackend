package com.oniesoft.service;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AdminViewService {

    Page<Branch> findBranchByCmpId(int id,String query, int page, int size);






    Page<Register> findRegisterByBranchId(int id, String query, int page, int size);
}
