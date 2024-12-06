package com.oniesoft.dto;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EditTestRunTestCasesDTO {
    private List<TestCaseDTO> testCases;
    private List<String> idsOfTestCasesInTestRun;
    private ApiResponse.PaginationMetadata pagination;
}
