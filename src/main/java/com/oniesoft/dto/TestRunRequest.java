package com.oniesoft.dto;

import java.util.List;

public class TestRunRequest {
    private int testRunId;
    private String testRunName;
    private String startedBy;
    private String status;
    private List<String> testCaseId;
    public TestRunRequest(){

    }

    public int getTestRunId() {
        return testRunId;
    }

    public void setTestRunId(int testRunId) {
        this.testRunId = testRunId;
    }

    public String getTestRunName() {
        return testRunName;
    }

    public void setTestRunName(String testRunName) {
        this.testRunName = testRunName;
    }

    public String getStartedBy() {
        return startedBy;
    }

    public void setStartedBy(String startedBy) {
        this.startedBy = startedBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getTestCaseId() {    return testCaseId;}
    public void setTestCaseId(List<String> testCaseId) {    this.testCaseId = testCaseId;}

    @Override
    public String toString() {
        return "TestRunRequest{" +
                "testRunId=" + testRunId +
                ", testRunName='" + testRunName + '\'' +
                ", startedBy='" + startedBy + '\'' +
                ", status='" + status + '\'' +
                ", testCaseId=" + testCaseId +
                '}';
    }
}
