package com.dineease.controller;
import com.dineease.dto.TableRequest;
import com.dineease.dto.TableResponse;
import com.dineease.service.ManageTableService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;

@Tag(name = "Restaurant - Table Management", description = "Chủ nhà hàng: Quản lý sơ đồ bàn")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/manage/tables")
@RequiredArgsConstructor

public class ManageTableController {
    private final ManageTableService manageTableService;

    @Operation(summary = "Lấy danh sách tất cả các bàn")
    @GetMapping
    public ResponseEntity<List<TableResponse>> getMyTables(Authentication auth) {
        String email = auth.getName();
        List<TableResponse> responses = manageTableService.getTablesByRestaurant(email);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Thêm một bàn mới vào sơ đồ")
    @PostMapping
    public ResponseEntity<TableResponse> createTable(
            @Valid @RequestBody TableRequest request, 
            Authentication auth) {
        
        String email = auth.getName();
        TableResponse response = manageTableService.createTable(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
}