package com.oniesoft.dto;
import java.time.LocalDateTime;

public class ProjectDTO {
    private long id;
    private String projectName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private int branchId;

    // Default constructor (you can leave this as it is)
    public ProjectDTO() {}

    // Constructor that initializes fields with values
    public ProjectDTO(long id, String projectName, LocalDateTime createdAt, LocalDateTime updatedAt, int branchId) {
        this.id = id;
        this.projectName = projectName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.branchId = branchId;
    }

    // Getters and setters (unchanged)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}
