package com.dineease.controller;
import com.dineease.dto.CheckInRequest;
import com.dineease.dto.ManageReservationResponse;
import com.dineease.service.ManageReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Restaurant - Reservation Management", description = "Chủ nhà hàng: Quản lý Đơn đặt bàn & Check-in")
@RestController
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/v1/manage/reservations")
public class ManageReservationController {
    private final ManageReservationService manageReservationService;

    public ManageReservationController(ManageReservationService manageReservationService) {
        this.manageReservationService = manageReservationService;
    }

    @Operation(summary = "Lấy danh sách Đơn đặt bàn của Quán", description = "Chỉ trả về các đơn thuộc nhà hàng của User đang đăng nhập")
    @GetMapping
    public ResponseEntity<Page<ManageReservationResponse>> getMyReservations(
            Authentication auth,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        // Sắp xếp đơn mới nhất (ngày đến ăn gần nhất) lên đầu
        Pageable pageable = PageRequest.of(page, size,
        Sort.by("reservationDate").ascending().and(Sort.by("reservationTime").ascending())
    );
        // auth.getName() chính là Email trích xuất từ JWT Token
        Page<ManageReservationResponse> responses =
        manageReservationService.getMyReservations(auth.getName(), pageable);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "Nhà hàng Xác nhận đơn (Duyệt)", description = "Chuyển đơn PENDING sang CONFIRMED")
    @PatchMapping("/{id}/approve")
    public ResponseEntity<ManageReservationResponse> approveReservation(
        @PathVariable Long id,
        Authentication auth) {
        ManageReservationResponse response =
        manageReservationService.approveReservation(id, auth.getName());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Đón khách (Check-in) & Xếp Bàn", description = "Chuyển đơn thành CHECKED_IN và đổi Bàn thành OCCUPIED")
    @PostMapping("/{id}/check-in")
    public ResponseEntity<ManageReservationResponse> checkInCustomer(
    @PathVariable Long id,
    @Valid @RequestBody CheckInRequest request,
    Authentication auth) {
        ManageReservationResponse response =
        manageReservationService.checkInCustomer(id, request.tableId(), auth.getName());
        return ResponseEntity.ok(response);
    }
}