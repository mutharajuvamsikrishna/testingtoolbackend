package com.oniesoft.serviceimpl;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Company;
import com.oniesoft.model.Project;
import com.oniesoft.model.Register;
import com.oniesoft.repository.BranchRepo;
import com.oniesoft.repository.CompanyRepo;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.RegisterRepo;
import com.oniesoft.service.AdminViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
public List<Branch> findBranchByCmpId(int id){
  return branchRepo.findByCmpId(id);
}
    public List<Register> findRegisterByBranchId(int id){
        return registerRepo.findByBranchId(id);
    }
}
