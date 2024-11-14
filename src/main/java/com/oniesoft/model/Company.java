package com.oniesoft.model;

import jakarta.persistence.*;

@Entity
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String cmpName;
    private String cmpId;
    private String cmpBranch;
public Company(){

}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCmpName() {
        return cmpName;
    }

    public void setCmpName(String cmpName) {
        this.cmpName = cmpName;
    }

    public String getCmpId() {
        return cmpId;
    }

    public void setCmpId(String cmpId) {
        this.cmpId = cmpId;
    }

    public String getCmpBranch() {
        return cmpBranch;
    }

    public void setCmpBranch(String cmpBranch) {
        this.cmpBranch = cmpBranch;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", cmpName='" + cmpName + '\'' +
                ", cmpId='" + cmpId + '\'' +
                ", cmpBranch='" + cmpBranch + '\'' +
                '}';
    }
}
