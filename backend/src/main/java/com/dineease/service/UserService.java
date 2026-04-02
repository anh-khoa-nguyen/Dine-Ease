package com.dineease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.UserResponse;
import com.dineease.entity.Role;
import com.dineease.entity.User;
import com.dineease.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> findAll(String keyword, Role role, Pageable pageable) {
        Page<User> users = userRepository.findAllByKeywordAndRole(keyword, role, pageable);
        return users.map(userMapper::toResponse);
    }
}