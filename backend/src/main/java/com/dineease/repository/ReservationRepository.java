package com.dineease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {}