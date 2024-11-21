package com.oniesoft.model;

import jakarta.persistence.*;

@Entity
public class TestRunAndTestCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_run_id", nullable = false)
    private TestRun testRun;

    @ManyToOne
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestRunAndCase testCase;
    public TestRunAndTestCase() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public TestRunAndCase getTestCase() {
        return testCase;
    }

    public void setTestCase(TestRunAndCase testCase) {
        this.testCase = testCase;
    }

    @Override
    public String toString() {
        return "TestRunAndTestCase{" +
                "id=" + id +
                ", testRun=" + testRun +
                ", testCase=" + testCase +
                '}';
    }
}
