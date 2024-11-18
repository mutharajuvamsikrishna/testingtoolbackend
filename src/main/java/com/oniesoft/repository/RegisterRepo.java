package com.oniesoft.repository;

import com.oniesoft.model.Register;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepo extends JpaRepository<Register,Integer> {

    Register findByEmpEmail(String empId);

    Register findByEmpMobAndEmpEmail(String empId, String empEmail);

    List<Register> findByBranchId(int id);
}
