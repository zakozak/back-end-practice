package com.project.diplom.controller;

import com.project.diplom.model.dto.MessageDto;
import com.project.diplom.model.dto.MessageFilteringViewDto;
import com.project.diplom.service.MessageService;
import com.project.diplom.shared.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<MessageFilteringViewDto>> getMessages(@RequestParam("destUserId") Long destUserId,
                                                                     Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();          //TODO если останется время то сделать пегинатион
        return ResponseEntity.ok(messageService.getMessages(customUserDetails.getId(), destUserId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> createMessage(@Valid @RequestBody MessageDto messageDetails,
                                                Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        messageService.createMessage(messageDetails, customUserDetails.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
