package com.oniesoft.repository;

import com.oniesoft.model.Register;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RegisterRepo extends JpaRepository<Register,Integer> {

    Register findByEmpEmail(String empId);

    Register findByEmpMobAndEmpEmail(String empId, String empEmail);

    Page<Register> findByBranchId(int id, Pageable pageable);
    List<Register> findByBranchId(int id);
    @Query("SELECT r FROM Register r WHERE "
            + "r.empName LIKE CONCAT('%', :query, '%') "
            + "OR r.empEmail LIKE CONCAT('%', :query, '%') "
            + "OR r.empMob LIKE CONCAT('%', :query, '%') "
            + "OR r.empRole LIKE CONCAT('%', :query, '%') "
            + "OR r.empDepartment LIKE CONCAT('%', :query, '%')")
    List<Register> searchRegisterDetails(@Param("query") String query);



}
