package com.oniesoft.dto;

public class RegisterDto {
    private String empId;
    private String empName;
    private String empEmail;
    private String empMob;
    private String empRole;
    private String empDepartment;
    private boolean status;
    private String reference;
    private String password;
    private String otp;

    public RegisterDto() {

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

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    @Override
    public String toString() {
        return "RegisterDto{" +
                "empId='" + empId + '\'' +
                ", empName='" + empName + '\'' +
                ", empEmail='" + empEmail + '\'' +
                ", empMob='" + empMob + '\'' +
                ", empRole='" + empRole + '\'' +
                ", empDepartment='" + empDepartment + '\'' +
                ", status=" + status +
                ", reference='" + reference + '\'' +
                ", password='" + password + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }
}
