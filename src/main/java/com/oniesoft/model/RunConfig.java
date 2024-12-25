package com.oniesoft.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@NoArgsConstructor
@Setter
@Getter
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
    private int userId;
}

