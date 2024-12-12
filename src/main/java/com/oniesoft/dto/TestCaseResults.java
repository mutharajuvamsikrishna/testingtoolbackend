package com.oniesoft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@AllArgsConstructor
@ToString
public class TestCaseResults {
    private int totalTestCases;
    private int pass;
    private int fail;
    private int skip;
    Map<String, Integer> featureOfPassPercent;
    Map<String, Map<String, Long>> resultsByTestType;
}
