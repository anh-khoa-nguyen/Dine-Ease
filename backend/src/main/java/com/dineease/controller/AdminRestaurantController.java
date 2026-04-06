package com.dineease.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dineease.dto.RestaurantAdminResponse;
import com.dineease.dto.RestaurantStatusUpdateRequest;
import com.dineease.service.AdminRestaurantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Admin - Restaurants", description = "Quản trị viên: Quản lý & Duyệt nhà hàng")
@RestController
@RequestMapping("/api/v1/admin/restaurants")
public class AdminRestaurantController {

    private final AdminRestaurantService adminRestaurantService;

    public AdminRestaurantController(AdminRestaurantService adminRestaurantService) {
        this.adminRestaurantService = adminRestaurantService;
    }

    @Operation(summary = "Lấy danh sách nhà hàng (Phân trang)")
    @GetMapping
    public ResponseEntity<Page<RestaurantAdminResponse>> getAllRestaurants(
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        Page<RestaurantAdminResponse> response = adminRestaurantService.getAllRestaurants(status, pageable);
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Cập nhật trạng thái (Duyệt/Từ chối nhà hàng)")
    @PatchMapping("/{id}/status")
    public ResponseEntity<RestaurantAdminResponse> updateRestaurantStatus(
            @PathVariable Long id,
            @Valid @RequestBody RestaurantStatusUpdateRequest request) {
        
        // Gọi Service update trạng thái (Tuần 5: Chỉ update DB)
        RestaurantAdminResponse updated = adminRestaurantService.updateRestaurantStatus(id, request);
        return ResponseEntity.ok(updated);
    }
}