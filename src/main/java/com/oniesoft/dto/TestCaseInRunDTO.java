package com.oniesoft.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCaseInRunDTO {
    private long id;
    private String testCaseName;
    private String status;
    private String author;
    private String automationId;
    private Long testCaseId;
    private String feature;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String traceStack;
    private String image;
    private Long executeTime;
    private String testType;
}
