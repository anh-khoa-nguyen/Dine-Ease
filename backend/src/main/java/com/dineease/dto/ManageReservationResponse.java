package com.dineease.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.dineease.entity.ReservationStatus;

public record ManageReservationResponse(
    Long id,
    String customerName,
    String customerPhone,
    LocalDate reservationDate,
    LocalTime reservationTime,
    Integer guestCount,
    String notes,
    ReservationStatus status,
    String assignedTableName
) {}
