package com.dineease.dto;

import com.dineease.entity.RestaurantStatus;

public record RestaurantAdminResponse(
    Long id,
    String name,
    String phoneContact,
    String address,
    Double commissionRate,
    RestaurantStatus status,
    String ownerEmail,  // Tiện cho Admin xem ai là chủ quán
    String ownerName
) {}