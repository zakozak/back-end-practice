package com.project.diplom.service.impl;

import com.project.diplom.model.dto.MessageDto;
import com.project.diplom.model.dto.MessageFilteringViewDto;
import com.project.diplom.model.entity.Message;
import com.project.diplom.repo.MessageRepository;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.service.MessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void createMessage(MessageDto messageDto, Long userId) {
        Message message = new Message();
        BeanUtils.copyProperties(messageDto, message);
        message.setContent(messageDto.getContent());
        message.setCreationTime(new Date());
        message.setUserId(userRepository.findUserById(userId));
        message.setDestUserId(userRepository.findUserById(messageDto.getRecipientUserId()));
        messageRepository.save(message);
    }

    @Override
    public List<MessageFilteringViewDto> getMessages(Long userId, Long destUserId) {
        List<Message> messages = messageRepository.findAllByUserIdAndDestUserId(userId, destUserId);
        List<MessageFilteringViewDto> messageFilteringViewDtos = new ArrayList<>();
        for (Message message : messages) {
            MessageFilteringViewDto messageFilteringViewDto = new MessageFilteringViewDto();
            messageFilteringViewDto.setContent(message.getContent());
            messageFilteringViewDto.setCreationTime(message.getCreationTime());
            messageFilteringViewDto.setUserId(message.getUserId().getId());
            messageFilteringViewDto.setUserDestId(message.getDestUserId().getId());
            messageFilteringViewDtos.add(messageFilteringViewDto);
        }
        return messageFilteringViewDtos;
    }
}
