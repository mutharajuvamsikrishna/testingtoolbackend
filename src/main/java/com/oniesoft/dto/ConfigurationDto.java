package com.oniesoft.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConfigurationDto {
    private String url;
    //	base url of api
    private String apiBaseURL;
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
}
