package com.project.diplom.service.impl;

import com.project.diplom.model.entity.User;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.shared.CustomUserDetails;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;


@Service
@Primary
public class AuthenticationServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public AuthenticationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) {
        User user = userRepository.findUserByName(name);
        return new CustomUserDetails(
                user.getId(),
                user.getEmail(),
                user.getUserRole(),
                user.getPassword());
    }
}
