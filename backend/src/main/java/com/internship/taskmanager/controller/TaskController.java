package com.internship.taskmanager.controller;


import com.internship.taskmanager.dto.TaskRequest;
import com.internship.taskmanager.dto.TaskResponse;
import com.internship.taskmanager.dto.TaskUpdateRequest;
import com.internship.taskmanager.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getProjectTasks(
            @PathVariable Long projectId,
            Authentication authentication) {
        String email = authentication.getName();
        List<TaskResponse> tasks = taskService.getProjectTasks(projectId, email);
        return ResponseEntity.ok(tasks);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        TaskResponse task = taskService.createTask(projectId, request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskUpdateRequest request,
            Authentication authentication) {
        String email = authentication.getName();
        TaskResponse task = taskService.updateTask(projectId, taskId, request, email);
        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<TaskResponse> markTaskAsCompleted(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Authentication authentication) {
        String email = authentication.getName();
        TaskResponse task = taskService.markTaskAsCompleted(projectId, taskId, email);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            Authentication authentication) {
        String email = authentication.getName();
        taskService.deleteTask(projectId, taskId, email);
        return ResponseEntity.noContent().build();
    }
}
