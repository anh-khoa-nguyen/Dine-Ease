package com.dineease.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Hoàng Yến & Thiện Đoan sẽ thêm: reservation_date, time, status, guest_count, deposit_amount...
}