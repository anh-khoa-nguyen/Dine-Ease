package com.dineease.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dineease.entity.CustomerProfile;
import com.dineease.entity.Reservation;
import com.dineease.entity.ReservationStatus;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByCustomer(CustomerProfile customer, Pageable pageable); 

    // Lấy danh sách đơn đặt bàn của quán dựa trên email chủ quán
    @EntityGraph(attributePaths = {"customer", "customer.user", "assignedTable"})
    Page<Reservation> findByRestaurantOwnerEmail(String email, Pageable pageable);

    // Tìm đơn cụ thể kèm check bảo mật email chủ quán
    Optional<Reservation> findByIdAndRestaurantOwnerEmail(Long id, String email);

    // 1. Tính TỔNG SỐ KHÁCH ĐÃ ĐẶT CHỖ tại 1 nhà hàng, vào đúng ngày/giờ đó.
    // Chỉ tính những đơn đang chiếm chỗ (PENDING, CONFIRMED, CHECKED_IN)
    @Query("""
        SELECT COALESCE(SUM(r.guestCount), 0) 
        FROM Reservation r 
        WHERE r.restaurant.id = :restaurantId 
          AND r.reservationDate = :reservationDate 
          AND r.reservationTime = :reservationTime
          AND r.status IN ('PENDING', 'CONFIRMED', 'CHECKED_IN')
    """)
    Integer getTotalReservedGuests(
        @Param("restaurantId") Long restaurantId,
        @Param("reservationDate") LocalDate reservationDate,
        @Param("reservationTime") LocalTime reservationTime
    );

    @Query("SELECT r FROM Reservation r WHERE r.id = :id AND r.customer.user.email = :email")
    Optional<Reservation> findByIdAndCustomerEmail(@Param("id") Long id, @Param("email") String email);

    // ---------
    // 1. Đếm tổng số đơn theo trạng thái (Dùng Spring Data JPA đếm tự động)
    long countByStatus(ReservationStatus status);

    // 2. Tính TỔNG TIỀN HOA HỒNG (Dùng COALESCE để tránh lỗi NullPointerException nếu chưa có doanh thu)
    @Query("SELECT COALESCE(SUM(r.commissionAmount), 0.0) FROM Reservation r WHERE r.status = :status")
    Double calculateTotalCommissionByStatus(@Param("status") ReservationStatus status);
}