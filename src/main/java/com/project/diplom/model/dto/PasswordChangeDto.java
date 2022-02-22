package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PasswordChangeDto {

    @Size(min = 8, max = 100, message = "Old password length from 8 to 100 symbols")
    @NotBlank
    private String oldPassword;
    @Size(min = 8, max = 100, message = "New password length from 8 to 100 symbols")
    @NotBlank
    private String newPassword;

}
