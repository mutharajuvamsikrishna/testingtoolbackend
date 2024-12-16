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
public Page<Branch> findBranchByCmpId(int id,String query, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        if(query.equalsIgnoreCase("Initial")){
            System.out.println("Yes");
       return   branchRepo.findByCmpId(id,pageable);
        }else {
            System.out.println("No");
           return branchRepo.searchBranchByCmpId(id,query,pageable);
        }
}

    @Override
    public Page<Register> findRegisterByBranchId(int id, String query, int page, int size){
    Pageable pageable = PageRequest.of(page, size);
    if(query.equalsIgnoreCase("Initial")) {
        return registerRepo.findByBranchId(id, pageable);
    }else {
        return registerRepo.searchRegisterDetails(id,query,pageable);
    }
    }
}
