package com.dineease.controller;
import com.dineease.dto.MenuItemRequest;
import com.dineease.dto.MenuItemResponse;
import com.dineease.service.ManageMenuService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

@Tag(name = "Restaurant - Menu Management", description = "Chủ nhà hàng: Quản lý thực đơn")
@RestController
@RequestMapping("/api/v1/manage/menu-items") 
@RequiredArgsConstructor
public class ManageMenuController {
    private final ManageMenuService manageMenuService;

    @GetMapping
    @Operation(summary = "Lấy danh sách món ăn của nhà hàng")
    public ResponseEntity<List<MenuItemResponse>> getMyMenuItems(Authentication auth) {
        String email = auth.getName(); 
        return ResponseEntity.ok(manageMenuService.getMenuItemsByRestaurant(email));
    }

    @PostMapping
    @Operation(summary = "Thêm một món ăn mới")
    public ResponseEntity<MenuItemResponse> createMenuItem(
            @Valid @RequestBody MenuItemRequest request, 
            Authentication auth) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(manageMenuService.createMenuItem(request, auth.getName()));
    }

    @PutMapping("/{itemId}")
    @Operation(summary = "Cập nhật thông tin món ăn")
    public ResponseEntity<MenuItemResponse> updateMenuItem(
            @PathVariable Long itemId, 
            @Valid @RequestBody MenuItemRequest request, 
            Authentication auth) {
        return ResponseEntity.ok(manageMenuService.updateMenuItem(itemId, request, auth.getName()));
    }

    @DeleteMapping("/{itemId}")
    @Operation(summary = "Xóa một món ăn")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long itemId, Authentication auth) {
        manageMenuService.deleteMenuItem(itemId, auth.getName());
        return ResponseEntity.noContent().build();
    }
}