package com.oniesoft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
    private int testRunId;
    private String status;
    private String automationId;
    private String executeTime;
    private String traceStack;
    private String image;
    private String testType;
}

