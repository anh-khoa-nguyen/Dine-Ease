package com.dineease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}