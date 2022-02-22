package com.project.diplom.shared;

import com.project.diplom.model.dto.ContactViewDto;
import com.project.diplom.model.entity.User;

public class ContactMapper {

    public static ContactViewDto mapToContactViewDto(User destUser) {
        return ContactViewDto.builder()
                .destUserId(destUser.getId())
                .destUserEmail(destUser.getEmail())
                .destUserName(destUser.getName())
                .destUserFirstName(destUser.getFirstName())
                .destUserLastName(destUser.getLastName())
                .destUserPhoneNumber(destUser.getPhoneNumber())
                .build();
    }
}
