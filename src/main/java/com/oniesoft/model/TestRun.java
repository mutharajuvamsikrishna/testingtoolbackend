package com.oniesoft.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Entity
public class TestRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String testRunName;
    private int testCaseCount;
    private String status;
    private String createdBy;
    private int userId;
    private String startedBy;
    private LocalDateTime startedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long projectId;


}
