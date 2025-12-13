package com.internship.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectDetailResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdDate;
    private Integer totalTasks;
    private Integer completedTasks;
    private Double progressPercentage;
    private List<TaskResponse> tasks;
}
