package com.dineease.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.RestaurantAdminResponse;
import com.dineease.dto.RestaurantStatusUpdateRequest;
import com.dineease.entity.Restaurant;
import com.dineease.entity.RestaurantStatus;
import com.dineease.entity.Role;
import com.dineease.entity.User;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.RestaurantRepository;
import com.dineease.repository.UserRepository;

@Service
public class AdminRestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    // Inject các dependency cần thiết
    public AdminRestaurantService(RestaurantRepository restaurantRepository,
                                  UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  EmailService emailService) {
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional(readOnly = true)
    public Page<RestaurantAdminResponse> getAllRestaurants(String status, Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return restaurants.map(this::mapToResponse);
    }

    // Gắn @Transactional để đảm bảo nếu gửi mail/lưu user lỗi thì CSDL sẽ Rollback
    @Transactional
    public RestaurantAdminResponse updateRestaurantStatus(Long id, RestaurantStatusUpdateRequest request) {
        // Bước 1: Lấy thông tin nhà hàng
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhà hàng", id));

        // Bước 2: Cập nhật trạng thái
        restaurant.setStatus(request.status());

        // Bước 3: Khi Admin DUYỆT nhà hàng
        if (request.status() == RestaurantStatus.APPROVED) {
            
            // Cập nhật mức hoa hồng (Commission)
            if (request.commissionRate() != null) {
                restaurant.setCommissionRate(request.commissionRate());
            }

            // Lấy thông tin chủ quán đã được liên kết với nhà hàng
            User owner = restaurant.getOwner();
            if (owner != null) {
                // A. Sinh ngẫu nhiên một mật khẩu 8 ký tự (Bỏ dấu gạch ngang của UUID)
                String rawPassword = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
                
                // B. Cập nhật tài khoản: Cấp quyền RESTAURANT và mã hóa mật khẩu mới
                owner.setRole(Role.RESTAURANT);
                owner.setPassword(passwordEncoder.encode(rawPassword));
                
                // C. Lưu User xuống DB
                userRepository.save(owner);

                // D. Gọi Service gửi Email (Chạy bất đồng bộ, không làm chậm API)
                emailService.sendApprovalEmail(owner.getEmail(), rawPassword, restaurant.getName());
            }
        }

        // Bước 4: Lưu thông tin nhà hàng
        restaurant = restaurantRepository.save(restaurant);

        return mapToResponse(restaurant);
    }

    // Hàm Helper (Mapping)
    private RestaurantAdminResponse mapToResponse(Restaurant restaurant) {
        return new RestaurantAdminResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getPhoneContact(),
                restaurant.getAddress(),
                restaurant.getCommissionRate(),
                restaurant.getStatus(),
                restaurant.getOwner() != null ? restaurant.getOwner().getEmail() : null,
                restaurant.getOwner() != null ? restaurant.getOwner().getFullName() : null
        );
    }
}