package com.project.diplom.controller;

import com.project.diplom.model.dto.TaskDto;
import com.project.diplom.model.dto.TaskViewDto;
import com.project.diplom.service.TaskService;
import com.project.diplom.shared.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/boards/{boardId}/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<TaskViewDto>> getTasks(@PathVariable Long boardId, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(taskService.getTasks(boardId, customUserDetails.getId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> createTask(@Valid @RequestBody TaskDto taskDetails,
                                             @PathVariable Long boardId,
                                             Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        taskService.createTask(taskDetails, customUserDetails.getId(), boardId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(path = "/{taskId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long taskId,
                                              @Valid @RequestBody TaskDto taskDetails,
                                              Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        taskService.updateTask(taskDetails, taskId, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(path = "/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        taskService.deleteTask(id, customUserDetails.getId());
        return ResponseEntity.noContent().build();
    }
}
