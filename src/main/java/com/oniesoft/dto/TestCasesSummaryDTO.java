package com.oniesoft.dto;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestCasesSummaryDTO {
    private Map<String, Long> totalRuns;
    private int totalPassed;
    private int totalFailed;
    private int totalSkipped;
    private List<Long> executionTimes;
}
