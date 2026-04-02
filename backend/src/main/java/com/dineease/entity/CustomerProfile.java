package com.dineease.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer_profiles")
@Getter
@Setter
public class CustomerProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: Hoàng Yến (Khách hàng) sẽ thêm các cột: loyalty_points, total_bookings, liên kết @OneToOne với User.
}