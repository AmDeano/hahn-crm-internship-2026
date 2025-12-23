package com.internship.taskmanager.service;

import com.internship.taskmanager.dto.*;
import com.internship.taskmanager.exception.ResourceNotFoundException;
import com.internship.taskmanager.model.Project;
import com.internship.taskmanager.model.User;
import com.internship.taskmanager.repository.ProjectRepository;
import com.internship.taskmanager.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<ProjectResponse> getUserProjects(String email) {
        User user = getUserByEmail(email);
        List<Project> projects = projectRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
        return projects.stream()
                .map(this::mapToProjectResponse)
                .collect(Collectors.toList());
    }

    public ProjectDetailResponse getProjectById(Long projectId, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        logger.debug("Retrieved project {} for user {}", projectId, email);
        return mapToProjectDetailResponse(project);
    }

    public ProjectResponse createProject(ProjectRequest request, String email) {
        User user = getUserByEmail(email);

        Project project = Project.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .build();

        project = projectRepository.save(project);
        logger.info("Created project {} for user {}", project.getId(), email);
        return mapToProjectResponse(project);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    }

    private ProjectResponse mapToProjectResponse(Project project) {
        int totalTasks = project.getTasks().size();
        int completedTasks = (int) project.getTasks().stream()
                .filter(task -> task.getIsCompleted())
                .count();

        return ProjectResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .progressPercentage(calculateProgress(totalTasks, completedTasks))
                .build();
    }

    private ProjectDetailResponse mapToProjectDetailResponse(Project project) {
        int totalTasks = project.getTasks().size();
        int completedTasks = (int) project.getTasks().stream()
                .filter(task -> task.getIsCompleted())
                .count();

        List<TaskResponse> tasks = project.getTasks().stream()
                .map(task -> TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .dueDate(task.getDueDate())
                        .isCompleted(task.getIsCompleted())
                        .createdAt(task.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return ProjectDetailResponse.builder()
                .id(project.getId())
                .title(project.getTitle())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .totalTasks(totalTasks)
                .completedTasks(completedTasks)
                .progressPercentage(calculateProgress(totalTasks, completedTasks))
                .tasks(tasks)
                .build();
    }

    private Double calculateProgress(int total, int completed) {
        if (total == 0) {
            return 0.0;
        }
        return Math.round((completed * 100.0 / total) * 100.0) / 100.0;
    }
}
