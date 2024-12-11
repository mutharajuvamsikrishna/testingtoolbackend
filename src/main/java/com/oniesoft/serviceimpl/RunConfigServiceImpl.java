package com.oniesoft.serviceimpl;

import com.oniesoft.dto.ConfigurationDto;
import com.oniesoft.exception.ResourceNotFoundException;
import com.oniesoft.model.Project;
import com.oniesoft.model.RunConfig;
import com.oniesoft.model.TestRun;
import com.oniesoft.repository.ProjectRepository;
import com.oniesoft.repository.RunConfigRepo;
import com.oniesoft.repository.TestRunRepo;
import com.oniesoft.service.RunConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RunConfigServiceImpl implements RunConfigService {
    @Autowired
    private RunConfigRepo runConfigRepo;
    @Autowired
    private TestRunRepo testRunRepo;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TestRunServiceImpl testRunService;

    @Override
    public RunConfig updateRunConfig(RunConfig runConfig) throws Exception {
        Optional<RunConfig> runConfig1 = runConfigRepo.findById(runConfig.getId());
        if (runConfig1.isPresent()) {
            if(runConfig1.get().isScheduleExecution()){
                TestRun testRun=testRunRepo.findById(runConfig1.get().getId()).get();
                testRun.setStatus("Scheduled");
                testRunRepo.save(testRun);
            }
            return runConfigRepo.save(runConfig);
        } else {
            throw new Exception("We Didn't Find RunConfig ID of " + runConfig.getId());
        }
    }

    @Override
    public Page<RunConfig> getAllPageRunConfig(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return runConfigRepo.findAll(pageable);
    }

    @Override
    public RunConfig getRunConfig(int id) {
        return runConfigRepo.findById(id).get();
    }

    @Override
    public RunConfig getRunConfigByTestRunId(int testRunId) {
        return runConfigRepo.findByTestRunId(testRunId);
    }

    @Override
    public String deleteRunConfig(int id) {
        runConfigRepo.deleteById(id);
        return "deleted SuccessFully";
    }

    @Override
    public ConfigurationDto getConfiguration(int testRunId) throws ResourceNotFoundException {
        // Fetch TestRun
        TestRun testRun = testRunRepo.findById(testRunId)
                .orElseThrow(() -> new ResourceNotFoundException("TestRun Id " + testRunId + " not found"));

        // Fetch Project
        Project project = projectRepository.findById(testRun.getProjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Project Id " + testRun.getProjectId() + " not found"));

        // Fetch RunConfig
        RunConfig runConfig = runConfigRepo.findByTestRunId(testRunId);
        if (runConfig == null) {
            throw new ResourceNotFoundException("RunConfig for TestRun Id " + testRunId + " not found");
        }

        // Construct and return ConfigurationDto
        return buildConfigurationDto(project, runConfig);
    }

    private ConfigurationDto buildConfigurationDto(Project project, RunConfig runConfig) {
        return new ConfigurationDto(
                project.getUrl(),
                project.getApiBaseURL(),
                project.isBasicAuth(),
                project.getBasicAuthUser(),
                project.getBasicAuthPassword(),
                project.isEnableLiveReporting(),
                project.getElasticSearchURL(),
                project.isNotifyTeams(),
                project.getNotifyBlockerCount(),
                project.getNotifyCriticalCount(),
                project.getNotifyMajorCount(),
                project.isSendEmailReport(),
                project.getEmailReportTo(),
                project.getJiraUserName(),
                project.getJiraPassword(),
                project.getJiraURL(),
                project.getJiraProjectKey(),
                runConfig.getBrowser(),
                runConfig.isHeadLess(),
                runConfig.isTraceView(),
                runConfig.isEnableRecording(),
                runConfig.getTestType(),
                runConfig.getShortWait(),
                runConfig.getCustomWait(),
                runConfig.getRetryCount(),
                runConfig.isOverrideReport(),
                runConfig.isCreateJiraIssues()
        );
    }

    @Scheduled(fixedRate = 60000) // Check every 10 seconds
    public void executeScheduledRuns() {
        System.out.println("Started ===============");
        LocalDateTime now = LocalDateTime.now();

        List<RunConfig> scheduledRuns = runConfigRepo.findByScheduleExecutionTrue();

        for (RunConfig runConfig : scheduledRuns) {
            if (runConfig.getScheduledDate() != null && runConfig.getScheduledTime() != null) {
                LocalDateTime scheduledDateTime = LocalDateTime.of(
                        runConfig.getScheduledDate(),
                        runConfig.getScheduledTime()
                );

                // Check if the scheduled time is within 1 second of the current time
                if (Duration.between(scheduledDateTime, now).abs().getSeconds() <= 60000) {
                    System.out.println("Test Execution Started at Scheduled Time");
                    runConfig.setScheduleExecution(false);
                    runConfigRepo.save(runConfig);
                    executeRun(runConfig);
                }
            }
        }
    }

    private void executeRun(RunConfig runConfig) {
        try {
            testRunService.integrateTestCasesWithTestingTool(runConfig.getTestRunId());
        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }
        System.out.println("Executing test run with ID: " + runConfig.getTestRunId());
    }
}
