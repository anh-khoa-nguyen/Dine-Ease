package com.dineease.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reservation_date", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "reservation_time", nullable = false)
    private LocalTime reservationTime;

    @Column(name = "guest_count", nullable = false)
    private Integer guestCount;

    @Column(columnDefinition = "TEXT")
    private String notes; // Ghi chú của khách

    @Column(name = "cancel_reason")
    private String cancelReason;

    @Column(name = "deposit_amount")
    @Builder.Default
    private Double depositAmount = 0.0;

    @Column(name = "final_total_amount")
    private Double finalTotalAmount;

    @Column(name = "commission_amount")
    private Double commissionAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private ReservationStatus status = ReservationStatus.PENDING;

    // AI là người đặt bàn
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private CustomerProfile customer;
    // Đặt ở NHÀ HÀNG nào
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
}