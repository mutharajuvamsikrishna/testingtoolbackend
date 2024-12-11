package com.oniesoft.dto;

import java.util.Map;

public class TestCaseResults {
    private int totalTestCases;
    private int pass;
    private int fail;
    private int skip;
    Map<String, Integer> featureOfPassPercent;

    public TestCaseResults(int totalTestCases, int pass, int fail, int skip, Map<String, Integer> featureOfPassPercent) {
        this.totalTestCases = totalTestCases;
        this.pass = pass;
        this.fail = fail;
        this.skip = skip;
        this.featureOfPassPercent = featureOfPassPercent;
    }

    public int getTotalTestCases() {
        return totalTestCases;
    }

    public int getPass() {
        return pass;
    }

    public int getFail() {
        return fail;
    }

    public int getSkip() {
        return skip;
    }

    public Map<String, Integer> getFeatureOfPassPercent() {
        return featureOfPassPercent;
    }
}
