package com.dineease.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dineease.dto.MenuCategoryPublicResponse;
import com.dineease.dto.RestaurantPublicResponse;
import com.dineease.service.PublicDiscoveryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//dành cho khach hàng chưa đăng nhập, xem được danh sách quán ăn, món ăn, đánh giá của quán ăn đó
@Tag(name = "Public - Discovery", description = "Khách hàng khám phá nhà hang và thực đơn mà không cần đăng nhập")
@RestController
@RequestMapping("/api/v1/public/restaurants")

public class PublicDiscoveryController {

    private final PublicDiscoveryService discoveryService;

    public PublicDiscoveryController(PublicDiscoveryService discoveryService) {
        this.discoveryService = discoveryService;
    }

    @Operation(summary = "Lấy danh sách nhà hàng hiển thị trang chủ")
    @GetMapping
    public ResponseEntity<Page<RestaurantPublicResponse>> getRestaurants(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("avgRating").descending());
        Page<RestaurantPublicResponse> restaurants = discoveryService.getAllPublicRestaurants(pageable);
        return ResponseEntity.ok(restaurants);
    }
    
    @Operation(summary = "Lấy thực đơn của nhà hàng cụ thể")
    @GetMapping("/{id}/menu")
    public ResponseEntity<List<MenuCategoryPublicResponse>> getRestaurantMenu(
        @PathVariable Long id
    ) {
        return ResponseEntity.ok(discoveryService.getRestaurantMenu(id));
    }
}
