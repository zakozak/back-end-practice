package com.project.diplom.service;

import com.project.diplom.model.dto.MessageDto;
import com.project.diplom.model.dto.MessageFilteringViewDto;

import java.util.List;

public interface MessageService {
    List<MessageFilteringViewDto> getMessages(Long userId, Long destUserId);

    void createMessage(MessageDto message, Long userId);
}
