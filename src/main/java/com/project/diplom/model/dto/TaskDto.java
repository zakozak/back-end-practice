package com.project.diplom.model.dto;

import com.project.diplom.model.entity.Task;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TaskDto {

    private Long id;

    @Size(min = 5, max = 50, message = "Min 5, max 50 symbols")
    @NotBlank
    private String title;

    @Size(min = 5, max = 250, message = "Min 5, max 250 symbols")
    @NotBlank
    private String description;

    @NotNull
    private Long assigneeId;

    @NotNull
    private Task.TaskStatus taskStatus;
}
