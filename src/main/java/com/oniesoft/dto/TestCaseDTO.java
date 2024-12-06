package com.oniesoft.dto;

import com.oniesoft.model.Project;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TestCaseDTO {
    private long id;
    private String testCaseName;
    private String author;
    private String automationId;
    private String feature;
    private Project project;
}
