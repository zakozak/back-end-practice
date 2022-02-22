package com.project.diplom.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactViewDto {
    private Long destUserId;
    private String destUserEmail;
    private String destUserName;
    private String destUserFirstName;
    private String destUserLastName;
    private String destUserPhoneNumber;
}
