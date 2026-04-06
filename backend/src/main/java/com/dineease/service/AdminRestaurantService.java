package com.dineease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.RestaurantAdminResponse;
import com.dineease.dto.RestaurantStatusUpdateRequest;
import com.dineease.entity.Restaurant;
import com.dineease.entity.RestaurantStatus;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.RestaurantRepository;

@Service
public class AdminRestaurantService {

    private final RestaurantRepository restaurantRepository;

    public AdminRestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    // Lấy danh sách phân trang (Tuần 5: Lấy tất cả, chưa cần filter phức tạp)
    @Transactional(readOnly = true)
    public Page<RestaurantAdminResponse> getAllRestaurants(String status, Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findAll(pageable);
        return restaurants.map(this::mapToResponse);
    }

    // Cập nhật trạng thái nhà hàng
    @Transactional
    public RestaurantAdminResponse updateRestaurantStatus(Long id, RestaurantStatusUpdateRequest request) {
        // Bước 1: Tìm Restaurant bằng ID. Nếu không thấy ném ResourceNotFoundException.
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nhà hàng", id));

        // Bước 2: Gán restaurant.setStatus(request.status())
        restaurant.setStatus(request.status());

        // Bước 3: Nếu status là APPROVED, gán thêm commissionRate nếu có gửi lên
        if (request.status() == RestaurantStatus.APPROVED && request.commissionRate() != null) {
            restaurant.setCommissionRate(request.commissionRate());
        }

        // Bước 4: Lưu xuống DB
        restaurant = restaurantRepository.save(restaurant);

        // Bước 5: Map từ Restaurant sang RestaurantAdminResponse và trả về.
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