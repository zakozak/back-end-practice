package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MessageFilteringViewDto {
    private String content;
    private Date creationTime;
    private Long userId;
    private Long userDestId;
}
