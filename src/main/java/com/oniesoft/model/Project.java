package com.oniesoft.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String projectName; // Default project name
    private LocalDateTime createdAt; // Default to current timestamp
    private LocalDateTime updatedAt; // Default to current timestamp
    private int branchId; // Default branch ID

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<TestCase> testCases;

    //	 PROJECT CONFIGURATIONS
    private String url;
    private String apiBaseURL;
    //	base url of api
    private boolean basicAuth;
    private String basicAuthUser;
    private String basicAuthPassword;
    // REPORT CONFIGURATIONS
// Enable Live Reporting using Elastic search and kibana so please provide the Elastic search URL
    private boolean enableLiveReporting;
    private String elasticSearchURL;
    //			 TEAMS NOTIFICATION
// send teams notifications based on the following configurations
    private boolean notifyTeams;
    private int notifyBlockerCount;
    private int notifyCriticalCount;
    private int notifyMajorCount;
    //	EMAIL NOTIFICATION
//send email report after test execution completes
    private boolean sendEmailReport;
    private String emailReportTo;
    //JIRA CONFIGURATIONS
//JIRA bug reporting
    private String jiraUserName;
    private String jiraPassword;
    private String jiraURL;
    private String jiraProjectKey;

    public Project() {

    }

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

    public List<TestCase> getTestCases() {
        return testCases;
    }

    public void setTestCases(List<TestCase> testCases) {
        this.testCases = testCases;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isBasicAuth() {
        return basicAuth;
    }

    public void setBasicAuth(boolean basicAuth) {
        this.basicAuth = basicAuth;
    }

    public String getBasicAuthUser() {
        return basicAuthUser;
    }

    public void setBasicAuthUser(String basicAuthUser) {
        this.basicAuthUser = basicAuthUser;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    public void setBasicAuthPassword(String basicAuthPassword) {
        this.basicAuthPassword = basicAuthPassword;
    }

    public boolean isEnableLiveReporting() {
        return enableLiveReporting;
    }

    public void setEnableLiveReporting(boolean enableLiveReporting) {
        this.enableLiveReporting = enableLiveReporting;
    }

    public String getElasticSearchURL() {
        return elasticSearchURL;
    }

    public void setElasticSearchURL(String elasticSearchURL) {
        this.elasticSearchURL = elasticSearchURL;
    }

    public boolean isNotifyTeams() {
        return notifyTeams;
    }

    public void setNotifyTeams(boolean notifyTeams) {
        this.notifyTeams = notifyTeams;
    }

    public int getNotifyBlockerCount() {
        return notifyBlockerCount;
    }

    public void setNotifyBlockerCount(int notifyBlockerCount) {
        this.notifyBlockerCount = notifyBlockerCount;
    }

    public int getNotifyCriticalCount() {
        return notifyCriticalCount;
    }

    public void setNotifyCriticalCount(int notifyCriticalCount) {
        this.notifyCriticalCount = notifyCriticalCount;
    }

    public int getNotifyMajorCount() {
        return notifyMajorCount;
    }

    public void setNotifyMajorCount(int notifyMajorCount) {
        this.notifyMajorCount = notifyMajorCount;
    }

    public boolean isSendEmailReport() {
        return sendEmailReport;
    }

    public void setSendEmailReport(boolean sendEmailReport) {
        this.sendEmailReport = sendEmailReport;
    }

    public String getEmailReportTo() {
        return emailReportTo;
    }

    public void setEmailReportTo(String emailReportTo) {
        this.emailReportTo = emailReportTo;
    }

    public String getJiraUserName() {
        return jiraUserName;
    }

    public void setJiraUserName(String jiraUserName) {
        this.jiraUserName = jiraUserName;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public void setJiraPassword(String jiraPassword) {
        this.jiraPassword = jiraPassword;
    }

    public String getJiraURL() {
        return jiraURL;
    }

    public void setJiraURL(String jiraURL) {
        this.jiraURL = jiraURL;
    }

    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    public void setJiraProjectKey(String jiraProjectKey) {
        this.jiraProjectKey = jiraProjectKey;
    }

    public String getApiBaseURL() {
        return apiBaseURL;
    }

    public void setApiBaseURL(String apiBaseURL) {
        this.apiBaseURL = this.apiBaseURL;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", branchId=" + branchId +
                ", testCases=" + testCases +
                ", url='" + url + '\'' +
                ", apiBaseURL='" + apiBaseURL + '\'' +
                ", basicAuth=" + basicAuth +
                ", basicAuthUser='" + basicAuthUser + '\'' +
                ", basicAuthPassword='" + basicAuthPassword + '\'' +
                ", enableLiveReporting=" + enableLiveReporting +
                ", elasticSearchURL='" + elasticSearchURL + '\'' +
                ", notifyTeams=" + notifyTeams +
                ", notifyBlockerCount=" + notifyBlockerCount +
                ", notifyCriticalCount=" + notifyCriticalCount +
                ", notifyMajorCount=" + notifyMajorCount +
                ", sendEmailReport=" + sendEmailReport +
                ", emailReportTo='" + emailReportTo + '\'' +
                ", jiraUserName='" + jiraUserName + '\'' +
                ", jiraPassword='" + jiraPassword + '\'' +
                ", jiraURL='" + jiraURL + '\'' +
                ", jiraProjectKey='" + jiraProjectKey + '\'' +
                '}';
    }
}
