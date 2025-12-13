package com.internship.taskmanager.controller;

import com.internship.taskmanager.dto.ProjectDetailResponse;
import com.internship.taskmanager.dto.ProjectRequest;
import com.internship.taskmanager.dto.ProjectResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.Authenticator;
import java.sql.Struct;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getUserProjects(Authentication authentication) {
        String email = authentication.getName();
        List<ProjectResponse> projects = projectService.GetUserProjects(email);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectDetailResponse> getProjectById(@PathVariable Long projectId, Authentication authentication) {
        String email = authentication.getName();
        ProjectDetailResponse project = projectService.getProjectById(projectId, email);
        return ResponseEntity.ok(project);
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody ProjectRequest request, Authentication authentication) {
        String email = authentication.getName();
        ProjectResponse project = projectService.createProject(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(project);
    }
}
