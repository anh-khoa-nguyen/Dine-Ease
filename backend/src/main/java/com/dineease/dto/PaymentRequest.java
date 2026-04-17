package com.dineease.dto;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
    @NotNull(message = "Mã đơn đặt bàn không được để trống")
    Long reservationId
) {}
