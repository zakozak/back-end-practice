package com.project.diplom.service;

import com.project.diplom.model.dto.ContactViewDto;

import java.util.Set;

public interface ContactService {

    Set<ContactViewDto> getContactViews(Long authId);

    void createContact(Long authId, Long destId);

    void deleteContact(Long authId, Long destId);
}
