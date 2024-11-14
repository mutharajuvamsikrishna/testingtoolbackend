package com.oniesoft.repository;

import com.oniesoft.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepo extends JpaRepository<Company,Integer> {

}
