package com.oniesoft.controller;


import com.oniesoft.model.Branch;
import com.oniesoft.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/branch/v1")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // Add a new branch
    @PostMapping("/save")
    public ResponseEntity<?> addBranch(@RequestBody Branch branch) {
        try {
            Branch addedBranch = branchService.addBranch(branch);
            return new ResponseEntity<>(addedBranch, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing branch
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBranch(@PathVariable int id, @RequestBody Branch branch) {
        try {
            branch.setId(id); // Ensure the branch ID is set for update
            Branch updatedBranch = branchService.updateBranch(branch);
            return new ResponseEntity<>(updatedBranch, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Get a branch by ID
    @GetMapping("/getbranchbyid/{id}")
    public ResponseEntity<?> getBranchById(@PathVariable int id) {
        try {
            Branch branch = branchService.getBranchById(id);
            return new ResponseEntity<>(branch, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    // Get all branches
    @GetMapping("/getallbranch")
    public ResponseEntity<List<Branch>> getAllBranches() {
        List<Branch> branches = branchService.getAllBranches();
        return new ResponseEntity<>(branches, HttpStatus.OK);
    }
}