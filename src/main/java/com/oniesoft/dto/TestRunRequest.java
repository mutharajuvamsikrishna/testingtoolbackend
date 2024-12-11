package com.oniesoft.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestRunRequest {
    private int testRunId;
    private String testRunName;
    private String startedBy;
    private String status;
    private List<String> testCaseId;
    private List<String> testCaseIdsToRemove;
}
