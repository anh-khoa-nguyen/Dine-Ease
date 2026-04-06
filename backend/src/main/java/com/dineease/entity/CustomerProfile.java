package com.dineease.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loyalty_points")
    @Builder.Default
    private Integer loyaltyPoints = 0; // Điểm tích lũy

    @Column(name = "total_bookings")
    @Builder.Default
    private Integer totalBookings = 0; // Tổng số lần đặt bàn

    //Khóa ngoại trỏ về bảng User (Mỗi User KH chỉ có thể là có 1 Profile)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;
}