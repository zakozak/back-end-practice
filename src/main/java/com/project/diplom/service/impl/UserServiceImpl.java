package com.project.diplom.service.impl;

import com.project.diplom.exception.DiplomaException;
import com.project.diplom.exception.ExceptionCode;
import com.project.diplom.model.dto.PasswordChangeDto;
import com.project.diplom.model.dto.UserDto;
import com.project.diplom.model.dto.UserFilteringDto;
import com.project.diplom.model.entity.User;
import com.project.diplom.repo.UserRepository;
import com.project.diplom.service.UserService;
import com.project.diplom.shared.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<UserFilteringDto> getUsers(String userNameContains) {
        if (StringUtils.isNotEmpty(userNameContains)) {
            return userRepository.findByNameContainingIgnoreCase(userNameContains)
                    .stream()
                    .map(UserMapper::mapToUserFilteringDto)
                    .collect(Collectors.toList());
        }

        return userRepository.findAll()
                .stream()
                .map(UserMapper::mapToUserFilteringDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserById(Long id, Long authUserId) {
        UserDto returnValue = new UserDto();
        User user = userRepository.findUserById(id);
        BeanUtils.copyProperties(user, returnValue);
        if (!id.equals(authUserId)) {
            throw new DiplomaException("User can get only his/her own account data", ExceptionCode.NO_PERMISSION);
        }
        return returnValue;
    }

    @Override
    public void createUser(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserRole(User.UserRole.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setVerificationStatus(true);
        user.setCreationTime(new Date());
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id, UserDto user, Long authUserId) {
        if (!id.equals(authUserId)) {
            throw new DiplomaException("User can update only his/her own account data", ExceptionCode.NO_PERMISSION);
        }
        User userEntity = userRepository.findUserById(id);
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setEmail(user.getEmail());
        userEntity.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(userEntity);
    }

    @Override
    public void changePassword(PasswordChangeDto passwordChangeDto, Long authUserId) {
        User user = userRepository.findUserById(authUserId);

        if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
            throw new DiplomaException("Old password is not correct!", ExceptionCode.VALIDATION_ERROR);
        }

        user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword()));
        userRepository.saveAndFlush(user);
    }
}
