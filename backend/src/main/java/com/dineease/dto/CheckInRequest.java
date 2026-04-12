package com.dineease.dto;

import jakarta.validation.constraints.NotNull;

public record CheckInRequest(
    @NotNull(message = "Vui lòng chọn bàn để check-in cho khách")
    Long tableId
) {}