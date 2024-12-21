package com.oniesoft.repository;

import com.oniesoft.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepo extends JpaRepository<Company,Integer> {






    Optional<Company> findByCmpNameOrCmpId(String cmpName, String cmpId);
}
