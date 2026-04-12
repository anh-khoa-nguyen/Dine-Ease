package com.dineease.dto;

public record AdminDashboardResponse(
    Long totalRestaurants,        // Tổng số nhà hàng đang Active
    Long totalReservations,       // Tổng số đơn đặt bàn (toàn hệ thống)
    Long successfulReservations,  // Số đơn đã hoàn thành (Thành công)
    Double totalCommissionRevenue // Tổng doanh thu hoa hồng của nền tảng
) {}