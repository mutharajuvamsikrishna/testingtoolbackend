package com.oniesoft.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
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

}
