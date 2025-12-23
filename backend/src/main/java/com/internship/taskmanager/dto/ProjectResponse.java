package com.internship.taskmanager.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer totalTasks;
    private Integer completedTasks;
    private Double progressPercentage;
}
