package com.dineease.dto;

import java.time.LocalTime;
import java.time.LocalDate;

import com.dineease.entity.ReservationStatus;

//Trả về để khách xem Lịch sử đặt bàn
public record ReservationResponse(
    Long id,
    Long restaurantId,
    String restaurantName,
    LocalDate reservationDate,
    LocalTime reservationTime,
    Integer guestCount,
    String notes,
    Double depositAmount,
    ReservationStatus status
) {
}
