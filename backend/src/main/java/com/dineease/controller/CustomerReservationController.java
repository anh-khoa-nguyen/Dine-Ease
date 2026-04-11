package com.dineease.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.dineease.dto.ReservationRequest;
import com.dineease.dto.ReservationResponse;
import com.dineease.service.CustomerReservationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

//Api đặt bàn (Yêu cầu token)
@Tag(name = "Customer - Reservation", description = "Khách hàng đặt bàn và xem lịch sử đặt bàn của mình")
@RestController
@RequestMapping("/api/v1/reservations")
public class CustomerReservationController {

    private final CustomerReservationService reservationService;

    public CustomerReservationController(CustomerReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @Operation(summary = "Tạo đơn đặt bàn mới")
    @PostMapping
    public ResponseEntity<ReservationResponse> createBooking(
        @Valid @RequestBody ReservationRequest request,
        @AuthenticationPrincipal UserDetails userDetails
    ) {
        String email = userDetails.getUsername();
        ReservationResponse response = reservationService.createReservation(request, email);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lấy lịch sử đặt bàn của tôi")
    @GetMapping
    public ResponseEntity<Page<ReservationResponse>> getMyHistory(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
        
        String email = userDetails.getUsername();
        Pageable pageable = PageRequest.of(page, size, Sort.by("reservationDate").descending());
        return ResponseEntity.ok(reservationService.getMyReservations(email,pageable));
    }
}
