package com.project.diplom.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFilteringDto {
    private Long id;
    private String email;
    private String name;
}
