package com.oniesoft.dto;

public class ConfigurationDto {
    private String url;
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
public ConfigurationDto(){

}

    public ConfigurationDto(String url, boolean basicAuth, String basicAuthUser, String basicAuthPassword, boolean enableLiveReporting, String elasticSearchURL, boolean notifyTeams, int notifyBlockerCount, int notifyCriticalCount, int notifyMajorCount, boolean sendEmailReport, String emailReportTo, String jiraUserName, String jiraPassword, String jiraURL, String jiraProjectKey, String browser, boolean headLess, boolean traceView, boolean enableRecording, String testType, int shortWait, int customWait, int retryCount, boolean overrideReport, boolean createJiraIssues) {
        this.url = url;
        this.basicAuth = basicAuth;
        this.basicAuthUser = basicAuthUser;
        this.basicAuthPassword = basicAuthPassword;
        this.enableLiveReporting = enableLiveReporting;
        this.elasticSearchURL = elasticSearchURL;
        this.notifyTeams = notifyTeams;
        this.notifyBlockerCount = notifyBlockerCount;
        this.notifyCriticalCount = notifyCriticalCount;
        this.notifyMajorCount = notifyMajorCount;
        this.sendEmailReport = sendEmailReport;
        this.emailReportTo = emailReportTo;
        this.jiraUserName = jiraUserName;
        this.jiraPassword = jiraPassword;
        this.jiraURL = jiraURL;
        this.jiraProjectKey = jiraProjectKey;
        this.browser = browser;
        this.headLess = headLess;
        this.traceView = traceView;
        this.enableRecording = enableRecording;
        this.testType = testType;
        this.shortWait = shortWait;
        this.customWait = customWait;
        this.retryCount = retryCount;
        this.overrideReport = overrideReport;
        this.createJiraIssues = createJiraIssues;
    }

    public String getUrl() {
        return url;
    }

    public boolean isBasicAuth() {
        return basicAuth;
    }

    public String getBasicAuthUser() {
        return basicAuthUser;
    }

    public String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    public boolean isEnableLiveReporting() {
        return enableLiveReporting;
    }

    public String getElasticSearchURL() {
        return elasticSearchURL;
    }

    public boolean isNotifyTeams() {
        return notifyTeams;
    }

    public int getNotifyBlockerCount() {
        return notifyBlockerCount;
    }

    public int getNotifyCriticalCount() {
        return notifyCriticalCount;
    }

    public int getNotifyMajorCount() {
        return notifyMajorCount;
    }

    public boolean isSendEmailReport() {
        return sendEmailReport;
    }

    public String getEmailReportTo() {
        return emailReportTo;
    }

    public String getJiraUserName() {
        return jiraUserName;
    }

    public String getJiraPassword() {
        return jiraPassword;
    }

    public String getJiraURL() {
        return jiraURL;
    }

    public String getJiraProjectKey() {
        return jiraProjectKey;
    }

    public String getBrowser() {
        return browser;
    }

    public boolean isHeadLess() {
        return headLess;
    }

    public boolean isTraceView() {
        return traceView;
    }

    public boolean isEnableRecording() {
        return enableRecording;
    }

    public String getTestType() {
        return testType;
    }

    public int getShortWait() {
        return shortWait;
    }

    public int getCustomWait() {
        return customWait;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public boolean isOverrideReport() {
        return overrideReport;
    }

    public boolean isCreateJiraIssues() {
        return createJiraIssues;
    }
}
