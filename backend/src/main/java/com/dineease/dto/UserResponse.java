package com.dineease.dto;

import java.time.Instant;
import com.dineease.entity.Role;

public record UserResponse(
    Long id,
    String email,
    String fullName,
    String phone,
    String avatarUrl,
    Role role,
    String status,
    Instant createdAt
){}