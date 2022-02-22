package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ContactCreationDto {
    @NotNull
    private Long destId;
}
