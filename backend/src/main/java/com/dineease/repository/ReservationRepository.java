package com.dineease.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dineease.entity.CustomerProfile;
import com.dineease.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByCustomer(CustomerProfile customer, Pageable pageable); //Lấy lịch sử đặt hàng của một khách hàng cụ thể
}