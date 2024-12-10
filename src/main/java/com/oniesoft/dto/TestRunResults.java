package com.oniesoft.dto;

import java.util.List;

public class TestRunResults {
    private int totalTestRuns;
    private int newStatusWithTestCases;
    private int newStatusWithOutTestCases;
    private int inProgressStatus;
    private int completed;
    private int scheduled;
    private TestRunResults(){

    }

    public TestRunResults(int totalTestRuns, int newStatusWithTestCases, int newStatusWithOutTestCases, int inProgressStatus, int completed, int scheduled) {
        this.totalTestRuns = totalTestRuns;
        this.newStatusWithTestCases = newStatusWithTestCases;
        this.newStatusWithOutTestCases = newStatusWithOutTestCases;
        this.inProgressStatus = inProgressStatus;
        this.completed = completed;
        this.scheduled = scheduled;
    }

    public int getTotalTestRuns() {
        return totalTestRuns;
    }

    public int getNewStatusWithTestCases() {
        return newStatusWithTestCases;
    }

    public int getNewStatusWithOutTestCases() {
        return newStatusWithOutTestCases;
    }

    public int getInProgressStatus() {
        return inProgressStatus;
    }

    public int getCompleted() {
        return completed;
    }

    public int getScheduled() {
        return scheduled;
    }
}
