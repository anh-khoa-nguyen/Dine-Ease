package com.dineease.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.dineease.dto.PaymentRequest;
import com.dineease.dto.PaymentUrlResponse;
import com.dineease.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Tag(name = "Customer - Payment", description = "Tạo thanh toán VNPay và Xử lý Webhook IPN")
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Operation(summary = "Tạo Link Thanh toán VNPay", description = "Tạo URL để chuyển hướng khách sang cổng VNPay.")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/vnpay")
    public ResponseEntity<PaymentUrlResponse> createVnpayPaymentUrl(
        @Valid @RequestBody PaymentRequest requestBody,
        Authentication auth,
        HttpServletRequest request // Cần để lấy địa chỉ IP của khách hàng
    ) {
        String email = auth.getName();
        PaymentUrlResponse response = paymentService.createVnpayPaymentUrl(requestBody.reservationId(), email, request);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Webhook (IPN) nhận kết quả từ VNPay", description = "API Public. Dành riêng cho Server VNPay gọi về để báo kết quả. Bảo mật bằng HmacSHA512.")
    @GetMapping("/webhook/vnpay")
    public ResponseEntity<String> processVnpayIpn(@RequestParam Map<String,String> allParams) {
        paymentService.processVnpayIpn(allParams);
 
        // Theo tài liệu VNPay, IPN phải trả về chuỗi JSON này để xác nhận đã nhận
        return ResponseEntity.ok("{\"RspCode\":\"00\",\"Message\":\"ConfirmSuccess\"}");
    }
}