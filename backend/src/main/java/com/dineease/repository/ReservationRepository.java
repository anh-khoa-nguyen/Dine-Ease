package com.dineease.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dineease.entity.CustomerProfile;
import com.dineease.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByCustomer(CustomerProfile customer, Pageable pageable); //Lấy lịch sử đặt hàng của một khách hàng cụ thể

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

    // 2.Tìm đơn đặt bàn để HỦY.
   // Bắt buộc phải tìm theo ID đơn VÀ Email của khách hàng đang đăng nhập (Tránh ng dùng khác hủy đơn của khách đặt)
   @Query(("SELECT r FROM Reservation r WHERE r.id = :id AND r.customer.user.email = :email"))
   Optional<Reservation> findByIdAndCustomerEmail(@Param("id") Long id,@Param("email") String email);
}