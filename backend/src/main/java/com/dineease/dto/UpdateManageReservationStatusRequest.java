package com.dineease.dto;

import com.dineease.entity.ReservationStatus;
import jakarta.validation.constraints.NotNull;
public record UpdateManageReservationStatusRequest(
    @NotNull(message = "Trạng thái không được để trống")
    ReservationStatus status,
    // Chỉ bắt buộc khi status = CHECKED_IN
    Long tableId
) {}