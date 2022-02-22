package com.project.diplom.shared;

import com.project.diplom.model.dto.TaskViewDto;
import com.project.diplom.model.entity.Task;

public class TaskMapper {

    public static TaskViewDto mapTaskViewDto(Task task) {
        TaskViewDto taskDto = new TaskViewDto();
        taskDto.setId(task.getId());
        taskDto.setTitle(task.getTitle());
        taskDto.setDescription(task.getDescription());
        taskDto.setTaskStatus(task.getTaskStatus());
        taskDto.setAssignee(UserMapper.mapToUserFilteringDto(task.getAssigneeId()));
        return taskDto;
    }
}
