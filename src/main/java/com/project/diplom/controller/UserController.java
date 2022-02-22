package com.project.diplom.controller;


import com.project.diplom.model.dto.PasswordChangeDto;
import com.project.diplom.model.dto.UserDto;
import com.project.diplom.model.dto.UserFilteringDto;
import com.project.diplom.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserFilteringDto>> getUsers(@RequestParam(name = "userNameContains", required = false) String destUserId,
                                                           Authentication authentication) {
        return ResponseEntity.ok(userService.getUsers(destUserId));
    }

    @PreAuthorize("hasAnyRole('USER')")
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> getUser(@PathVariable Long id, Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userService.getUserById(id, customUserDetails.getId()));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserDto userDetails) {
        userService.createUser(userDetails);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @Valid @RequestBody UserDto userDetails,
                                              Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        userService.updateUser(id, userDetails, customUserDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAnyRole('USER')")
    @PutMapping(path = "/password-change", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> changePassword(@Valid @RequestBody PasswordChangeDto passwordChangeDto,
                                              Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        userService.changePassword(passwordChangeDto, customUserDetails.getId());
        return ResponseEntity.ok().build();
    }
}
