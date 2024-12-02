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
	private  LocalDateTime createdAt; // Default to current timestamp
	private LocalDateTime updatedAt; // Default to current timestamp
	private int branchId; // Default branch ID

	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<TestCase> testCases;

	private String url; // Default URL
	private String apiBaseURL;
	// BASIC AUTH CONFIGURATIONS
	@Column(columnDefinition = "boolean default false")
	private boolean basicAuth = false;
	private String basicAuthUser; // Default empty
	private String basicAuthPassword; // Default empty

	// RUNNER CONFIGURATIONS
	private String browser = "chrome"; // Default browser
	@Column(columnDefinition = "boolean default false")
	private boolean headLess = false;
	@Column(columnDefinition = "boolean default false")
	private boolean traceView = false;
	@Column(columnDefinition = "boolean default false")
	private boolean enableRecording = false;
	private String testType; // Default test type
	@Column(columnDefinition = "int default 10")
	private int shortWait = 10; // Default short wait in seconds
	@Column(columnDefinition = "int default 30")
	private int customWait = 30; // Default custom wait in seconds
	@Column(columnDefinition = "int default 3")
	private int retryCount = 3; // Default retry count

	// REPORT CONFIGURATIONS
	@Column(columnDefinition = "boolean default false")
	private boolean enableLiveReporting = false;
	private String elasticSearchURL; // Default empty
	@Column(columnDefinition = "boolean default false")
	private boolean overrideReport;

	// TEAMS NOTIFICATION
	@Column(columnDefinition = "boolean default false")
	private boolean notifyTeams;
	@Column(columnDefinition = "int default 5")
	private int notifyBlockerCount = 5; // Default blocker count threshold
	@Column(columnDefinition = "int default 10")
	private int notifyCriticalCount = 10; // Default critical count threshold
	@Column(columnDefinition = "int default 20")
	private int notifyMajorCount = 20; // Default major count threshold

	// EMAIL NOTIFICATION
	@Column(columnDefinition = "boolean default false")
	private boolean sendEmailReport = false;
	private String emailReportTo; // Default empty

	// JIRA CONFIGURATIONS
	@Column(columnDefinition = "boolean default false")
	private boolean createJiraIssues = false;
	private String jiraUserName; // Default empty
	private String jiraPassword; // Default empty
	private String jiraURL; // Default empty
	private String jiraProjectKey; // Default empty
public Project(){

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

	public String getApiBaseURL() {
		return apiBaseURL;
	}

	public void setApiBaseURL(String apiBaseURL) {
		this.apiBaseURL = apiBaseURL;
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

	public boolean isOverrideReport() {
		return overrideReport;
	}

	public void setOverrideReport(boolean overrideReport) {
		this.overrideReport = overrideReport;
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

	public boolean isCreateJiraIssues() {
		return createJiraIssues;
	}

	public void setCreateJiraIssues(boolean createJiraIssues) {
		this.createJiraIssues = createJiraIssues;
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
				", browser='" + browser + '\'' +
				", headLess=" + headLess +
				", traceView=" + traceView +
				", enableRecording=" + enableRecording +
				", testType='" + testType + '\'' +
				", shortWait=" + shortWait +
				", customWait=" + customWait +
				", retryCount=" + retryCount +
				", enableLiveReporting=" + enableLiveReporting +
				", elasticSearchURL='" + elasticSearchURL + '\'' +
				", overrideReport=" + overrideReport +
				", notifyTeams=" + notifyTeams +
				", notifyBlockerCount=" + notifyBlockerCount +
				", notifyCriticalCount=" + notifyCriticalCount +
				", notifyMajorCount=" + notifyMajorCount +
				", sendEmailReport=" + sendEmailReport +
				", emailReportTo='" + emailReportTo + '\'' +
				", createJiraIssues=" + createJiraIssues +
				", jiraUserName='" + jiraUserName + '\'' +
				", jiraPassword='" + jiraPassword + '\'' +
				", jiraURL='" + jiraURL + '\'' +
				", jiraProjectKey='" + jiraProjectKey + '\'' +
				'}';
	}
}
