package com.dineease.service;

import org.springframework.stereotype.Component;
import com.dineease.dto.UserResponse;
import com.dineease.entity.User;

@Component
public class UserMapper {
    public UserResponse toResponse(User user) {
        if (user == null) return null;

        return new UserResponse(
            user.getId(),
            user.getEmail(),
            user.getFullName(),
            user.getPhone(),
            user.getAvatarUrl(),
            user.getRole(),
            user.getStatus(),
            user.getCreatedAt()
        );
    }
}