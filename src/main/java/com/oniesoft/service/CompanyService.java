package com.oniesoft.service;

import com.oniesoft.model.Company;

import java.util.List;

public interface CompanyService {
    Company createCompany(Company company) throws Exception;

    Company upDateCompant(Company company) throws Exception;

    Company getCompanyById(int id) throws Exception;

    List<Company> getAllCompanies();
}
