package com.dineease.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.AdminDashboardResponse;
import com.dineease.entity.ReservationStatus;
import com.dineease.entity.RestaurantStatus;
import com.dineease.repository.ReservationRepository;
import com.dineease.repository.RestaurantRepository;

@Service
public class AdminReportService {

    private final RestaurantRepository restaurantRepository;
    private final ReservationRepository reservationRepository;

    public AdminReportService(RestaurantRepository restaurantRepository, ReservationRepository reservationRepository) {
        this.restaurantRepository = restaurantRepository;
        this.reservationRepository = reservationRepository;
    }

    // @Transactional(readOnly = true) giúp tối ưu hóa tốc độ khi chỉ đọc dữ liệu
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboardStats() {
        
        // 1. Đếm tổng số nhà hàng ĐANG HOẠT ĐỘNG
        long totalRestaurants = restaurantRepository.countByStatus(RestaurantStatus.ACTIVE);
        
        // 2. Đếm tổng số đơn đặt bàn trên TOÀN BỘ hệ thống (bất kể trạng thái)
        long totalReservations = reservationRepository.count();
        
        // 3. Đếm số đơn đặt bàn ĐÃ HOÀN THÀNH (Khách đã ăn xong và thanh toán)
        long successfulReservations = reservationRepository.countByStatus(ReservationStatus.COMPLETE);
        
        // 4. Tính tổng doanh thu hoa hồng từ các đơn ĐÃ HOÀN THÀNH
        Double totalCommissionRevenue = reservationRepository.calculateTotalCommissionByStatus(ReservationStatus.COMPLETE);

        // Gom tất cả vào Record DTO và trả về
        return new AdminDashboardResponse(
            totalRestaurants,
            totalReservations,
            successfulReservations,
            totalCommissionRevenue
        );
    }
}