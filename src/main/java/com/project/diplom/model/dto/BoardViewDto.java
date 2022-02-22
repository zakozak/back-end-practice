package com.project.diplom.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardViewDto {
    private Long id;
    private String title;
    private String description;
    private UserFilteringDto owner;
    private List<UserFilteringDto> users;
}
