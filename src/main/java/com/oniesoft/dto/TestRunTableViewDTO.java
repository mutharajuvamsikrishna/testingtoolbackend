package com.oniesoft.dto;

import com.oniesoft.model.TestRun;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestRunTableViewDTO {
    private int id;
    private String testRunName;
    private String createdBy;
    private int testCaseCount;
    private String status;
}
