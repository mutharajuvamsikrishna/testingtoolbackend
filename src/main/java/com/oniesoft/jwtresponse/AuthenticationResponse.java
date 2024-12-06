package com.oniesoft.jwtresponse;

import com.oniesoft.model.Register;

public class AuthenticationResponse {
    private String jwt;
    private String empName;
    private String empEmail;
    private String empMob;
    private String empRole;
    private String empDepartment;
    private boolean status;
    private int branchId;

    // Constructors, getters, and setters

    public AuthenticationResponse(String jwt, String empName, String empEmail, String empMob, String empRole, String empDepartment, boolean status, int branchId) {
        this.jwt = jwt;
        this.empName = empName;
        this.empEmail = empEmail;
        this.empMob = empMob;
        this.empRole = empRole;
        this.empDepartment = empDepartment;
        this.status = status;
        this.branchId = branchId;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpMob() {
        return empMob;
    }

    public void setEmpMob(String empMob) {
        this.empMob = empMob;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public String getEmpDepartment() {
        return empDepartment;
    }

    public void setEmpDepartment(String empDepartment) {
        this.empDepartment = empDepartment;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}