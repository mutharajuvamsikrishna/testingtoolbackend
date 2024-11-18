package com.oniesoft.model;

import jakarta.persistence.*;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String branchName;
    private String branchId;
   private int cmpId;
    public Branch(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public int getCmpId() {
        return cmpId;
    }

    public void setCmpId(int cmpId) {
        this.cmpId = cmpId;
    }

    @Override
    public String toString() {
        return "Branch{" +
                "id=" + id +
                ", branchName='" + branchName + '\'' +
                ", branchId='" + branchId + '\'' +
                ", cmpId=" + cmpId +
                '}';
    }
}
