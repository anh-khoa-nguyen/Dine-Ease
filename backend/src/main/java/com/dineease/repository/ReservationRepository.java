package com.dineease.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dineease.entity.CustomerProfile;
import com.dineease.entity.Reservation;
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByCustomer(CustomerProfile customer, Pageable pageable); 
    Page<Reservation> findByRestaurantOwnerEmail(String email, Pageable pageable);
    Optional<Reservation> findByIdAndRestaurantOwnerEmail(Long id, String email);

} 