package com.oniesoft.serviceimpl;

import com.oniesoft.model.Company;
import com.oniesoft.repository.CompanyRepo;
import com.oniesoft.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepo companyRepo;
    @Override
    public Company createCompany(Company company) throws Exception {
        Optional<Company> company1 = companyRepo.findById(company.getId());
        if (company1.isPresent()) {
            throw new Exception("Company Already Registered");
        } else {
            return companyRepo.save(company);
        }
    }
    @Override
public Company upDateCompant(Company company) throws Exception {
    Optional<Company> company1 = companyRepo.findById(company.getId());
    if (company1.isPresent()) {
        return companyRepo.save(company);

    } else {
        throw new Exception("Company id "+company.getId()+" not find");
    }
}
@Override
    public Company getCompanyById(int id) throws Exception {
        Optional<Company> company=companyRepo.findById(id);
        if(company.isPresent()){
                     return  company.get();
        }else {
            throw new Exception("for the Id "+id+" not match any records");
        }
}
@Override
    public List<Company> getAllCompanies(){
        return companyRepo.findAll();
}
@Override
    public String deleteCompany(int id){
        companyRepo.deleteById(id);
        return "Delete Successfully";
    }
}

