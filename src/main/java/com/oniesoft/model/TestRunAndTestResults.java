package com.oniesoft.model;

import jakarta.persistence.*;

@Entity
public class TestRunAndTestResults {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "test_run_id", nullable = false)
    private TestRun testRun;

    @ManyToOne
    @JoinColumn(name = "test_result_id", nullable = false)
    private TestResults testResults;

    public TestRunAndTestResults() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public TestResults getTestResults() {
        return testResults;
    }

    public void setTestResults(TestResults testResults) {
        this.testResults = testResults;
    }

    @Override
    public String toString() {
        return "TestRunAndTestResults{" +
                "id=" + id +
                ", testRun=" + testRun +
                ", testResults=" + testResults +
                '}';
    }
}

