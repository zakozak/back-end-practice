package com.project.diplom.service;

import com.project.diplom.model.dto.PasswordChangeDto;
import com.project.diplom.model.dto.UserDto;
import com.project.diplom.model.dto.UserFilteringDto;

import java.util.List;

public interface UserService {

    List<UserFilteringDto> getUsers(String userNameContains);

    UserDto getUserById(Long id, Long authUserId);

    void createUser(UserDto user);

    void updateUser(Long id, UserDto user, Long authUserId);

    void changePassword(PasswordChangeDto passwordChangeDto, Long authUserId);
}
