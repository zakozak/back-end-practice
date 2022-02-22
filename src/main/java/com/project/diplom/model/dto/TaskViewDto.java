package com.project.diplom.model.dto;

import com.project.diplom.model.entity.Task;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TaskViewDto {
    private Long id;
    private String title;
    private String description;
    private UserFilteringDto assignee;
    private Task.TaskStatus taskStatus;
}
