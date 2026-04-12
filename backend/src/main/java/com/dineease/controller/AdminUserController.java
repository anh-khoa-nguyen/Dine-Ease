package com.dineease.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dineease.dto.UserResponse;
import com.dineease.entity.Role;
import com.dineease.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin - Users Management", description = "Quản trị viên: Xem danh sách người dùng")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/admin/users")
public class AdminUserController {
    
    private final UserService userService;

    public AdminUserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Lấy danh sách người dùng (Phân trang, Lọc)")
    @GetMapping()
    public ResponseEntity<Page<UserResponse>> listUsers(
        @RequestParam(required = false) String keyword,
        @RequestParam(required = false) Role role,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sort,
        @RequestParam(defaultValue = "desc") String order
    ) {
        Sort.Direction direction = "asc".equalsIgnoreCase(order) ? Sort.Direction.ASC : Sort.Direction.DESC; 
        Pageable pageable = PageRequest.of(page, Math.min(size, 100), Sort.by(direction, sort));
        
        Page<UserResponse> users = userService.findAll(keyword, role, pageable);
        return ResponseEntity.ok(users);
    }
}