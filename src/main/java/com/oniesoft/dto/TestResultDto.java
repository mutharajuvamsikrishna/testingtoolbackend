package com.oniesoft.dto;

import java.time.LocalDateTime;


public class TestResultDto {
    private int testRunId;
    private String testCaseName;
    private String status;
    private String author;
    private String automationId;
    private String feature;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
  private String excuteTime;
public TestResultDto(){

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getExcuteTime() {
        return excuteTime;
    }

    public void setExcuteTime(String excuteTime) {
        this.excuteTime = excuteTime;
    }

    @Override
    public String toString() {
        return "TestResultDto{" +
                "testRunId=" + testRunId +
                ", testCaseName='" + testCaseName + '\'' +
                ", status='" + status + '\'' +
                ", author='" + author + '\'' +
                ", automationId='" + automationId + '\'' +
                ", feature='" + feature + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", excuteTime='" + excuteTime + '\'' +
                '}';
    }
}
