package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class BoardDto {
    @Size(min = 5, max = 50, message = "Title length from 3 to 40 symbols")
    @NotBlank
    private String title;

    @Size(min = 3, max = 250, message = "Description length from 3 to 250 symbols")
    @NotBlank
    private String description;

    private Set<Long> userIds;
}
