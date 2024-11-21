package com.oniesoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
public class TestResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int testRunId;
    private String testCaseName;
    private String author;
    private String automationId;
    private String feature;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private String excuteTime;

    public TestResults(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(int testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAutomationId() {
        return automationId;
    }

    public void setAutomationId(String automationId) {
        this.automationId = automationId;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }

    @Override
    public String toString() {
        return "TestResults{" +
                "id=" + id +
                ", testRunId=" + testRunId +
                ", testCaseName='" + testCaseName + '\'' +
                ", author='" + author + '\'' +
                ", automationId='" + automationId + '\'' +
                ", feature='" + feature + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", status='" + status + '\'' +
                ", excuteTime='" + excuteTime + '\'' +
                '}';
    }
}
