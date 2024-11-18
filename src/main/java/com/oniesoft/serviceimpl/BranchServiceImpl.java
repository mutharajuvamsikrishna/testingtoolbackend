package com.oniesoft.serviceimpl;

import com.oniesoft.model.Branch;
import com.oniesoft.repository.BranchRepo;
import com.oniesoft.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class BranchServiceImpl implements BranchService {
    @Autowired
    private BranchRepo branchRepo;
@Override
    public Branch addBranch(Branch branch) throws Exception {
        Branch branch1 = branchRepo.findByBranchId(branch.getBranchId());
        if (branch1 == null) {
            return branchRepo.save(branch);
        } else {
            throw new Exception("Branch Already Exists");
        }
    }
    @Override
    public Branch updateBranch(Branch branch) throws Exception {
        Optional<Branch> branch1 = branchRepo.findById(branch.getId());
        if (branch1.isPresent()) {
            return branchRepo.save(branch1.get());
        } else {
            throw new Exception("Branch Already Exists");
        }
    }
    @Override
    public Branch getBranchById(int branchId) throws Exception {
        Optional<Branch> branch= branchRepo.findById(branchId);
        if(branch.isPresent()){
            return branch.get();
        }else{
            throw new Exception("Required Id "+branchId+" Not Found");
        }
    }
    @Override
    public List<Branch> getAllBranches(){
        return branchRepo.findAll();
    }
}
