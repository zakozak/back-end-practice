package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class MessageDto {
    @Size(min = 2, max = 250, message = "Min 2, max 250 symbols")
    @NotBlank
    private String content;
    @NotNull
    private Long recipientUserId;
}
