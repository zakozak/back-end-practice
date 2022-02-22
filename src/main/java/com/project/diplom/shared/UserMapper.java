package com.project.diplom.shared;

import com.project.diplom.model.dto.UserFilteringDto;
import com.project.diplom.model.entity.User;

public class UserMapper {

    public static UserFilteringDto mapToUserFilteringDto(User user) {
        return UserFilteringDto.builder().id(user.getId()).email(user.getEmail()).name(user.getName()).build();
    }
}
