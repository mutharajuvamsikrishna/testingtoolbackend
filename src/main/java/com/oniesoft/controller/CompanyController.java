package com.oniesoft.controller;

import com.oniesoft.model.Company;
import com.oniesoft.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cmp/v1")
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @PostMapping("/addcompany")
    public ResponseEntity<?> addCompany(@RequestBody Company company) {
        try {
            Company createdCompany = companyService.createCompany(company);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            // Log the exception (optional)
            // Return a response with an error message and HTTP status 400 (Bad Request)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/updatecompany")
    public ResponseEntity<?> updateCompany(@RequestBody Company company) {
        try {
            Company createdCompany = companyService.upDateCompant(company);
            return new ResponseEntity<>(createdCompany, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            // Log the exception (optional)
            // Return a response with an error message and HTTP status 400 (Bad Request)
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getcmpbyid")
    public ResponseEntity<?> getcmpById(@RequestParam int id){
        try {
            Company company=companyService.getCompanyById(id);
            return new ResponseEntity<>(company,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getallcmp")
    public List<Company> getAllCompany(){
        return companyService.getAllCompanies();
    }
}
