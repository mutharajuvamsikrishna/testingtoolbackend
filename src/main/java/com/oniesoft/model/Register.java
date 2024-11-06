package com.oniesoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
@Entity
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String empId;
    private String empName;
    private String empEmail;
    private String empMob;
    private String empRole;
    private String empDepartment;
    private boolean status;
    private String password;
public Register(){

}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Register{" +
                "id=" + id +
                ", empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empMob='" + empMob + '\'' +
                ", empRole='" + empRole + '\'' +
                ", empDepartment='" + empDepartment + '\'' +
                ", status=" + status +
                ", password='" + password + '\'' +
                '}';
    }
}
