package com.oniesoft.serviceimpl;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;
import com.oniesoft.repository.BranchRepo;
import com.oniesoft.repository.CompanyRepo;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.service.AdminViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminViewServiceImpl implements AdminViewService {
    @Autowired
    private CompanyRepo companyRepo;
    @Autowired
    private BranchRepo branchRepo;
    @Autowired
    private RegisterRepo registerRepo;
    @Autowired
    private ProjectRepository projectRepository;
    @Override
public Page<Branch> findBranchByCmpId(int id, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
  return branchRepo.findByCmpId(id,pageable);
}
@Override
public List<Branch> searchBranch(String query){
        return branchRepo.searchBranchDetails(query);
}

@Override
    public Page<Register> findRegisterByBranchId(int id, int page, int size){
    Pageable pageable = PageRequest.of(page, size);
        return registerRepo.findByBranchId(id,pageable);
    }
}
