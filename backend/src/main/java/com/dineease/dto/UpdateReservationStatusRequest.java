package com.dineease.dto;

import com.dineease.entity.ReservationStatus;

import jakarta.validation.constraints.NotNull;

public record UpdateReservationStatusRequest(
    @NotNull(message = "Trạng thái không được để trống")
    ReservationStatus status,
    // Lý do hủy (Chỉ bắt buộc nếu status là CANCELLED)
    String cancelReason
) {}
