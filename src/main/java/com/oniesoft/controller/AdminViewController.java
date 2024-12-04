package com.oniesoft.controller;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;
import com.oniesoft.service.AdminViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/adminview/v1")
public class AdminViewController {
    @Autowired
    private AdminViewService adminViewService;
    @GetMapping("/branchsbycmpid")
    public Page<Branch> findBranchsByCmpId(@RequestParam int cmpId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return adminViewService.findBranchByCmpId(cmpId,page,size);
    }

        @GetMapping("/registersbybranchId")
    public Page<Register> findRegistersByBranchId(@RequestParam int branchId,@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        return adminViewService.findRegisterByBranchId(branchId,page,size);
    }
    @GetMapping("/searchbranch")
    public List<Branch> searchBranch(@RequestParam String query){
        return adminViewService.searchBranch(query);
    }
}
