package com.oniesoft.serviceimpl;

import com.oniesoft.dto.TestCaseResults;
import com.oniesoft.dto.TestCasesSummaryDTO;
import com.oniesoft.dto.TestRunResults;
import com.oniesoft.model.TestRun;
import com.oniesoft.model.TestRunAndCase;
import com.oniesoft.model.TestRunAndTestCase;
import com.oniesoft.repository.TestRunAndCaseRepo;
import com.oniesoft.repository.TestRunAndTestCaseRepo;
import com.oniesoft.repository.TestRunRepo;
import com.oniesoft.service.ChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChartServiceImpl implements ChartService {
    @Autowired
    private TestRunAndCaseRepo testRunAndCaseRepo;
    @Autowired
    private TestRunAndTestCaseRepo testRunAndTestCaseRepo;
    @Autowired
    private TestRunRepo testRunRepo;

    @Override
    public TestRunResults getAllTestResultsStatus(long projectId) {
        List<TestRun> testRuns = testRunRepo.findByProjectId(projectId);
        // Count the statuses using Java Streams
        int totalTestRuns = testRuns.size();
        int newStatusWithTestCases = (int) testRuns.stream().filter(testRun -> ("New".equalsIgnoreCase(testRun.getStatus())) && testRun.getTestCaseCount() > 0).count();
        int newStatusWithOutTestCases = (int) testRuns.stream().filter(testRun -> ("New".equalsIgnoreCase(testRun.getStatus())) && testRun.getTestCaseCount() < 0).count();
        int inProgressStatus = (int) testRuns.stream().filter(testRun -> "InProgress".equalsIgnoreCase(testRun.getStatus())).count();
        int completed = (int) testRuns.stream().filter(testRun -> "Completed".equalsIgnoreCase(testRun.getStatus())).count();
        int scheduled = (int) testRuns.stream().filter(testRun -> "Scheduled".equalsIgnoreCase(testRun.getStatus())).count();

        // Return the results wrapped in the TestRunResults DTO
        return new TestRunResults(totalTestRuns, newStatusWithTestCases, newStatusWithOutTestCases, inProgressStatus, completed, scheduled);
    }

    @Override
    public TestCaseResults getTestCaseResultsByTRId(int testRunId) {
        // Fetch all TestRunAndCase entries by testRunId
        List<TestRunAndCase> testRunAndTestCases = testRunAndTestCaseRepo.findTestCasesByTestRunId(testRunId);

        // Calculate totals and statuses
        int totalTestCases = testRunAndTestCases.size();
        int pass = (int) testRunAndTestCases.stream().filter(tc -> "Pass".equalsIgnoreCase(tc.getStatus())).count();
        int fail = (int) testRunAndTestCases.stream().filter(tc -> "Fail".equalsIgnoreCase(tc.getStatus())).count();
        int skip = (int) testRunAndTestCases.stream().filter(tc -> "Skip".equalsIgnoreCase(tc.getStatus())).count();

        Map<String, Integer> featureOfPassPercent = getFeatureWisePassPercentage(testRunAndTestCases);

        // Create and return a TestCaseResults object
        return new TestCaseResults(totalTestCases, pass, fail, skip, featureOfPassPercent, getResultsByTestType(testRunAndTestCases));
    }

    @Override
    public TestCasesSummaryDTO getTestCaseSummaryFromAllRuns(Long testCaseId) {
        List<TestRunAndCase> testRunAndCases = testRunAndCaseRepo.findByTestCaseId(testCaseId);
        List<Optional<TestRunAndTestCase>> trtcs = testRunAndCases.stream().map(trc -> testRunAndTestCaseRepo.findById((int) trc.getId())).toList();
        Map<String, Long> completedRuns = trtcs.stream()
                .filter(trc -> trc.isPresent() && trc.get().getTestRun() != null && trc.get().getTestRun().getStatus() != null)
                .collect(Collectors.groupingBy(
                        trc -> trc.get().getTestRun().getStatus(),
                        Collectors.counting()
                ));
        TestCasesSummaryDTO testCasesSummaryDTO = new TestCasesSummaryDTO();
        testCasesSummaryDTO.setTotalRuns(completedRuns);
        testCasesSummaryDTO.setTotalPassed((int) testRunAndCases.stream().filter(trc -> trc.getStatus().equalsIgnoreCase("pass")).count());
        testCasesSummaryDTO.setTotalPassed((int) testRunAndCases.stream().filter(trc -> trc.getStatus().equalsIgnoreCase("fail")).count());
        testCasesSummaryDTO.setTotalPassed((int) testRunAndCases.stream().filter(trc -> trc.getStatus().equalsIgnoreCase("skip")).count());
        testCasesSummaryDTO.setExecutionTimes(testRunAndCases.stream().filter(trc -> trc.getExecuteTime() != null &&
                !trc.getExecuteTime().equalsIgnoreCase("0 ms")).map(trc ->  Long.parseLong(Optional.ofNullable(trc.getExecuteTime()).orElse("0").replaceAll("[^\\d.]", ""))).collect(Collectors.toList()));
        return testCasesSummaryDTO;
    }

    private Map<String, Integer> getFeatureWisePassPercentage(List<TestRunAndCase> testRunAndTestCases) {

        // Group test cases by feature
        Map<String, List<TestRunAndCase>> groupedByFeature = testRunAndTestCases.stream()
                .collect(Collectors.groupingBy(TestRunAndCase::getFeature));

        // Calculate pass percentage for each feature
        Map<String, Integer> featureWisePassPercent = new HashMap<>();

        for (Map.Entry<String, List<TestRunAndCase>> entry : groupedByFeature.entrySet()) {
            String feature = entry.getKey();
            List<TestRunAndCase> testCases = entry.getValue();

            int totalTestCases = testCases.size();
            int passCount = (int) testCases.stream().filter(tc -> "Pass".equalsIgnoreCase(tc.getStatus())).count();

            int passPercent = (totalTestCases > 0) ? (passCount * 100 / totalTestCases) : 0;
            featureWisePassPercent.put(feature, passPercent);
        }

        return featureWisePassPercent;
    }

    private Map<String, Map<String, Long>> getResultsByTestType(List<TestRunAndCase> testRunAndTestCases) {
        // calculate pass, fail and skip count for each test type
        // Group by Test Type
        // Group Pass/Fail/Skip within each case
        // Count Pass/Fail/Skip
        return testRunAndTestCases.stream()
                .collect(Collectors.groupingBy(
                       testCase -> testCase.getTestType() != null ? testCase.getTestType() : "",
                        Collectors.groupingBy( // Group Pass/Fail/ within each case
                                TestRunAndCase::getStatus,
                                Collectors.counting() // Count Pass/Fail
                        )
                ));
    }


}
