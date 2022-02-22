package com.project.diplom.service;

import com.project.diplom.model.dto.TaskDto;
import com.project.diplom.model.dto.TaskViewDto;

import java.util.List;


public interface TaskService {
    void createTask(TaskDto task, Long userId, Long boardId);

    List<TaskViewDto> getTasks(Long boardId, Long userId);

    void updateTask(TaskDto task, Long taskId, Long userId);

    void deleteTask(Long taskId, Long userId);
}
