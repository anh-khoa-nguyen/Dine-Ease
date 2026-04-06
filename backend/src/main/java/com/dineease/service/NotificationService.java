package com.dineease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.NotificationRequest;
import com.dineease.dto.NotificationResponse;
import com.dineease.entity.CampaignStatus;
import com.dineease.entity.NotificationCampaign;
import com.dineease.entity.User;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.NotificationCampaignRepository;
import com.dineease.repository.UserRepository;

@Service
public class NotificationService {

    private final NotificationCampaignRepository campaignRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationCampaignRepository campaignRepository, UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.userRepository = userRepository;
    }

    // Tạo chiến dịch mới
    @Transactional
    public NotificationResponse createCampaign(NotificationRequest request, String adminEmail) {
        // Bước 1: Lấy adminId từ user đang đăng nhập (thông qua email lấy từ SecurityContext)
        User admin = userRepository.findByEmail(adminEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Tài khoản Admin không tồn tại"));

        // Bước 2: Tạo Entity NotificationCampaign từ DTO
        NotificationCampaign campaign = NotificationCampaign.builder()
                .title(request.title())
                .content(request.content())
                .targetAudience(request.targetAudience())
                .channel(request.channel())
                .scheduledTime(request.scheduledTime())
                // Trạng thái mặc định: SCHEDULED hoặc PROCESSING
                .status(request.scheduledTime() != null ? CampaignStatus.SCHEDULED : CampaignStatus.PROCESSING)
                .admin(admin)
                .build();

        // Bước 3: Lưu xuống DB
        campaign = campaignRepository.save(campaign);

        // Bước 4: Map ra Response và trả về.
        return mapToResponse(campaign);
    }

    // Lấy danh sách chiến dịch
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getAllCampaigns(Pageable pageable) {
        return campaignRepository.findAll(pageable).map(this::mapToResponse);
    }

    // Hàm Helper (Mapping)
    private NotificationResponse mapToResponse(NotificationCampaign campaign) {
        return new NotificationResponse(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getContent(),
                campaign.getTargetAudience(),
                campaign.getChannel(),
                campaign.getStatus(),
                campaign.getScheduledTime(),
                campaign.getAdmin() != null ? campaign.getAdmin().getEmail() : null
        );
    }
}