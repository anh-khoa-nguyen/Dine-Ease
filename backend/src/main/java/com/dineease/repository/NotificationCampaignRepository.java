package com.dineease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.NotificationCampaign;

public interface NotificationCampaignRepository extends JpaRepository<NotificationCampaign, Long> {
}