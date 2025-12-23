package com.internship.taskmanager.service;

import com.internship.taskmanager.dto.TaskRequest;
import com.internship.taskmanager.dto.TaskResponse;
import com.internship.taskmanager.dto.TaskUpdateRequest;
import com.internship.taskmanager.model.Project;
import com.internship.taskmanager.model.Task;
import com.internship.taskmanager.model.User;
import com.internship.taskmanager.repository.ProjectRepository;
import com.internship.taskmanager.repository.TaskRepository;
import com.internship.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<TaskResponse> getProjectTasks(Long projectId, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        return project.getTasks().stream()
                .map(this::mapToTaskResponse)
                .collect(Collectors.toList());
    }

    public TaskResponse createTask(Long projectId, TaskRequest request, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .dueDate(request.getDueDate())
                .isCompleted(false)
                .project(project)
                .build();

        task = taskRepository.save(task);
        return mapToTaskResponse(task);
    }

    public TaskResponse updateTask(Long projectId, Long taskId, TaskUpdateRequest request, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (request.getTitle() != null) {
            task.setTitle(request.getTitle());
        }
        if (request.getDescription() != null) {
            task.setDescription(request.getDescription());
        }
        if (request.getDueDate() != null) {
            task.setDueDate(request.getDueDate());
        }
        if (request.getIsCompleted() != null) {
            task.setIsCompleted(request.getIsCompleted());
        }

        task = taskRepository.save(task);
        return mapToTaskResponse(task);
    }

    public void deleteTask(Long projectId, Long taskId, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        taskRepository.delete(task);
    }

    public TaskResponse markTaskAsCompleted(Long projectId, Long taskId, String email) {
        User user = getUserByEmail(email);
        Project project = projectRepository.findByIdAndUserId(projectId, user.getId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Task task = taskRepository.findByIdAndProjectId(taskId, projectId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setIsCompleted(true);
        task = taskRepository.save(task);
        return mapToTaskResponse(task);
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private TaskResponse mapToTaskResponse(Task task) {
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .isCompleted(task.getIsCompleted())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
