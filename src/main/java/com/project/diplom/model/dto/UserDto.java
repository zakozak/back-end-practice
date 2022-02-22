package com.project.diplom.model.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class UserDto {
    @Email(message = "Email should be valid")
    @Size(min = 5, max = 100, message = "Min 5, max 100 symbols")
    @NotBlank
    private String email;

    @Size(min = 5, max = 50, message = "Min 5, max 50 symbols")
    @NotBlank
    private String name;

    @Size(min = 5, max = 50, message = "Min 5, max 50 symbols")
    private String firstName;

    @Size(min = 5, max = 50, message = "Min 5, max 50 symbols")
    private String lastName;

    @Size(min = 8, max = 100, message = "Password length from 8 to 100 symbols")
    @NotBlank
    private String password;

    @Size(min = 9, max = 12, message = "Phone number length from 8 to 12 symbols")
    private String phoneNumber;
}
