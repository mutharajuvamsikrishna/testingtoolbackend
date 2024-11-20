package com.oniesoft.controller;

import com.oniesoft.model.Branch;
import com.oniesoft.model.Register;
import com.oniesoft.service.AdminViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/adminview/v1")
public class AdminViewController {
    @Autowired
    private AdminViewService adminViewService;
    @GetMapping("/branchsbycmpid/{cmpId}")
    public List<Branch> findBranchsByCmpId(@PathVariable int cmpId){
        return adminViewService.findBranchByCmpId(cmpId);
    }
    @GetMapping("/registersbybranchId/{branchId}")
    public List<Register> findRegistersByBranchId(@PathVariable int branchId){
        return adminViewService.findRegisterByBranchId(branchId);
    }
}
