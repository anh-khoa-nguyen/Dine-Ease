package com.dineease.entity;

import java.time.Instant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_read", nullable = false)
    @Builder.Default
    private Boolean isRead = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    // Gửi cho ai?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Thuộc chiến dịch nào? (Có thể null nếu hệ thống tự gửi như Đặt bàn thành công)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id")
    private NotificationCampaign campaign;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }
}