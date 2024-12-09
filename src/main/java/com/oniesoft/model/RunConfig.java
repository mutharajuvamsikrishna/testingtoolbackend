package com.oniesoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class RunConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int testRunId;
    private String browser;
    private boolean headLess;
    private boolean traceView;
    private boolean enableRecording;
    //            testType = api or web or mobile or all (default). all will run all type of tests
    private String testType;

    private int shortWait;

    //customWait is used to override normalWait and longWait in any waiting scenario
    private int customWait;
    //            retryCount will retry given number of times for failed tests default value is 0
    private int retryCount;
    //            Override Extent report with existing report
    private boolean overrideReport;
    //            JIRA CONFIGURATIONS
// JIRA bug reporting
    private boolean createJiraIssues;

    // Separate fields for scheduling

    private boolean scheduleExecution;  // Flag to enable/disable scheduling
    private LocalDate scheduledDate;    // Execution date
    private LocalTime scheduledTime;    // Execution time

    public RunConfig() {

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

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public boolean isHeadLess() {
        return headLess;
    }

    public void setHeadLess(boolean headLess) {
        this.headLess = headLess;
    }

    public boolean isTraceView() {
        return traceView;
    }

    public void setTraceView(boolean traceView) {
        this.traceView = traceView;
    }

    public boolean isEnableRecording() {
        return enableRecording;
    }

    public void setEnableRecording(boolean enableRecording) {
        this.enableRecording = enableRecording;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }

    public int getShortWait() {
        return shortWait;
    }

    public void setShortWait(int shortWait) {
        this.shortWait = shortWait;
    }

    public int getCustomWait() {
        return customWait;
    }

    public void setCustomWait(int customWait) {
        this.customWait = customWait;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public boolean isOverrideReport() {
        return overrideReport;
    }

    public void setOverrideReport(boolean overrideReport) {
        this.overrideReport = overrideReport;
    }

    public boolean isCreateJiraIssues() {
        return createJiraIssues;
    }

    public void setCreateJiraIssues(boolean createJiraIssues) {
        this.createJiraIssues = createJiraIssues;
    }

    public boolean isScheduleExecution() {
        return scheduleExecution;
    }

    public void setScheduleExecution(boolean scheduleExecution) {
        this.scheduleExecution = scheduleExecution;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    @Override
    public String toString() {
        return "RunConfig{" +
                "id=" + id +
                ", testRunId=" + testRunId +
                ", browser='" + browser + '\'' +
                ", headLess=" + headLess +
                ", traceView=" + traceView +
                ", enableRecording=" + enableRecording +
                ", testType='" + testType + '\'' +
                ", shortWait=" + shortWait +
                ", customWait=" + customWait +
                ", retryCount=" + retryCount +
                ", overrideReport=" + overrideReport +
                ", createJiraIssues=" + createJiraIssues +
                ", scheduleExecution=" + scheduleExecution +
                ", scheduledDate=" + scheduledDate +
                ", scheduledTime=" + scheduledTime +
                '}';
    }
}

