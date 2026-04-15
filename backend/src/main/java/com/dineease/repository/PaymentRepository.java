package com.dineease.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    // Tìm giao dịch thanh toán dựa vào Mã đơn hàng (vnp_TxnRef) gửi đi cho VNPay
    Optional<Payment> findByTransactionCode(String transactionCode);
    
    // Tìm giao dịch thanh toán (nếu có) của một Đơn đặt bàn cụ thể
    Optional<Payment> findByReservationId(Long reservationId);
}