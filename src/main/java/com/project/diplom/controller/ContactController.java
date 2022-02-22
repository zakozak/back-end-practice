package com.project.diplom.controller;

import com.project.diplom.model.dto.ContactCreationDto;
import com.project.diplom.model.dto.ContactViewDto;
import com.project.diplom.service.ContactService;
import com.project.diplom.shared.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Set<ContactViewDto>> getContacts(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(contactService.getContactViews(customUserDetails.getId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> createContact(@Valid @RequestBody ContactCreationDto creationDto, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        contactService.createContact(customUserDetails.getId(), creationDto.getDestId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping(path = "/{destId}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<Object> deleteContact(@PathVariable("destId") Long destId, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        contactService.deleteContact(customUserDetails.getId(), destId);
        return ResponseEntity.noContent().build();
    }
}
