package com.oniesoft.dto;

public class UserConfigDto {
    private long id;
    private String url;
    //	base url of api
    private String projectName;
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

    private String ipAddress;
    private String projectPath;

    public UserConfigDto(long id, String url,String projectName, boolean basicAuth, String basicAuthUser, String basicAuthPassword, boolean enableLiveReporting, String elasticSearchURL, boolean notifyTeams, int notifyBlockerCount, int notifyCriticalCount, int notifyMajorCount, boolean sendEmailReport, String emailReportTo, String jiraUserName, String jiraPassword, String jiraURL, String jiraProjectKey, String ipAddress, String projectPath) {
        this.id = id;
        this.url = url;
        this.projectName=projectName;
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
        this.ipAddress = ipAddress;
        this.projectPath = projectPath;
    }

    public String getProjectName() {
        return projectName;
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


    public String getIpAddress() {
        return ipAddress;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
