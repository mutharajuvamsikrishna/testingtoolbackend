package com.oniesoft.repository;

import com.oniesoft.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepo extends JpaRepository<Register,Integer> {

    Register findByEmpId(String empId);

    Register findByEmpIdAndEmpEmail(String empId, String empEmail);
}
