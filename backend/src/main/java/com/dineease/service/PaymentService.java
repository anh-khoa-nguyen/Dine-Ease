package com.dineease.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.config.VnpayConfig;
import com.dineease.dto.PaymentUrlResponse;
import com.dineease.entity.Payment;
import com.dineease.entity.PaymentMethod;
import com.dineease.entity.PaymentStatus;
import com.dineease.entity.PaymentType;
import com.dineease.entity.Reservation;
import com.dineease.entity.ReservationStatus;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.PaymentRepository;
import com.dineease.repository.ReservationRepository;
import com.dineease.util.VnpayUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaymentService {
    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final VnpayConfig vnpayConfig;

    public PaymentService(ReservationRepository reservationRepository, PaymentRepository paymentRepository, VnpayConfig vnpayConfig) {
        this.reservationRepository = reservationRepository;
        this.paymentRepository = paymentRepository;
        this.vnpayConfig = vnpayConfig;
    }

   @Transactional
    public PaymentUrlResponse createVnpayPaymentUrl(Long reservationId, String customerEmail, HttpServletRequest request) {
        Reservation reservation = reservationRepository.findByIdAndCustomerEmail(reservationId, customerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Đơn đặt bàn không tồn tại hoặc không thuộc về khách hàng!"));
        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Đơn hàng không ở trạng thái chờ thanh toán!");
        }
        long amount = (reservation.getDepositAmount() > 0 ? Math.round(reservation.getDepositAmount()) : 100000L) * 100;
        String vnp_TxnRef = reservation.getId() + "_" + System.currentTimeMillis();
        String vnp_IpAddr = VnpayUtil.getIpAddress(request);
        String vnp_OrderInfo = "Thanh toan dat coc cho don hang " + reservationId;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnpayConfig.getTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.getReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();

        for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue,StandardCharsets.US_ASCII));
                query.append(URLEncoder.encode(fieldName,StandardCharsets.US_ASCII)).append('=').append(URLEncoder.encode(fieldValue,StandardCharsets.US_ASCII));
                if(itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = VnpayUtil.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        String paymentUrl = vnpayConfig.getPayUrl() + "?" + query.toString();

        Payment payment = Payment.builder() 
                .reservation(reservation)
                .paymentType(PaymentType.DEPOSIT)
                .paymentMethod(PaymentMethod.VNPAY)
                .status(PaymentStatus.PENDING)
                .amount((double) amount / 100)
                .transactionCode(vnp_TxnRef)
                .build();
        paymentRepository.save(payment);
 
        return new PaymentUrlResponse(paymentUrl);
    }
    @Transactional
    public void processVnpayIpn(Map<String, String> vnp_Params) {
        try {
            String vnp_SecureHash = vnp_Params.get("vnp_SecureHash");
            vnp_Params.remove("vnp_SecureHash");
            vnp_Params.remove("vnp_SecureHashType");

            List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
            Collections.sort(fieldNames);
            StringBuilder hashData = new StringBuilder();

            for (Iterator<String> itr = fieldNames.iterator(); itr.hasNext();) {
                String fieldName = itr.next();
                String fieldValue = vnp_Params.get(fieldName);
                if (fieldValue != null && !fieldValue.isEmpty()) {
                    hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue,StandardCharsets.US_ASCII));
                    if (itr.hasNext()) {
                        hashData.append('&');
                    }
                }
            }
            String computedSignature = VnpayUtil.hmacSHA512(vnpayConfig.getHashSecret(), hashData.toString());
            if (computedSignature.equals(vnp_SecureHash)) {
                String vnp_TxnRef = vnp_Params.get("vnp_TxnRef");
                Payment payment = paymentRepository.findByTransactionCode(vnp_TxnRef)
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giao dịch: " + vnp_TxnRef));
 
                if (payment.getStatus() == PaymentStatus.SUCCESS) return;
                if ("00".equals(vnp_Params.get("vnp_ResponseCode"))) {
                    payment.setStatus(PaymentStatus.SUCCESS);
                    Reservation reservation = payment.getReservation();
                    reservation.setStatus(ReservationStatus.CONFIRMED);
                    reservationRepository.save(reservation);
                    log.info("Thanh toán VNPay thành công. Đã cập nhật đơn hàng:{}", reservation.getId());
                } else {
                    payment.setStatus(PaymentStatus.FAILED);
                    log.info("Thanh toán VNPay thất bại cho mã giao dịch: {}",vnp_TxnRef);
                }
                paymentRepository.save(payment);
            } else {
                log.error("NGUY HIỂM: Chữ ký IPN của VNPay không hợp lệ!");
            }
        } catch (Exception e) {
            log.error("Lỗi xử lý IPN của VNPay: {}", e.getMessage());
        }
    }

}
