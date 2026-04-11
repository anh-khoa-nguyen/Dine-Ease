package com.dineease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.ReservationRequest;
import com.dineease.dto.ReservationResponse;
import com.dineease.entity.CustomerProfile;
import com.dineease.entity.Reservation;
import com.dineease.entity.ReservationStatus;
import com.dineease.entity.Restaurant;
import com.dineease.entity.User;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.CustomerProfileRepository;
import com.dineease.repository.ReservationRepository;
import com.dineease.repository.RestaurantRepository;
import com.dineease.repository.UserRepository;
//Phục vụ cho khách đặt bàn (đã đăng nhập)
@Service
public class CustomerReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final UserRepository userRepository;

    public CustomerReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, CustomerProfileRepository customerProfileRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.userRepository = userRepository;
    }
    // Tạo đơn đặt bàn mới
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, String customerEmail) {
        //1. Tìm CustomerProfile theo email
        CustomerProfile profile = customerProfileRepository.findByUserEmail(customerEmail).orElseGet(() -> {
            User user = userRepository.findByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));
            CustomerProfile newProfile = CustomerProfile.builder().user(user).build();
            return customerProfileRepository.save(newProfile);
        });
        //2.Tìm restaurant theo request.restaurantId
        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
            .orElseThrow(() -> new ResourceNotFoundException("Nhà hàng", request.restaurantId()));
        //3. Tạo Entity Reservation  và lưu xuống DB (Mặc định status = PENDING) - tạo đơn đặt (chưa bao gồm check kẹt bàn)

        Reservation reservation = Reservation.builder()
            .customer(profile)
            .restaurant(restaurant)
            .reservationDate(request.reservationDate())
            .reservationTime(request.reservationTime())
            .guestCount(request.guestCount())
            .notes(request.notes())
            .status(ReservationStatus.PENDING) //Mặc định là chờ xác nhận
            .build();
        reservation =  reservationRepository.save(reservation);
        return mapToResponse(reservation);
    }

    //Xem lịch sử đặt bàn của tôi
    @Transactional(readOnly = true)
    public Page<ReservationResponse> getMyReservations(String customerEmail, Pageable pageable) {
        //1. Tìm CustomerProfile theo email
        CustomerProfile profile = customerProfileRepository.findByUserEmail(customerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("CustomerProfile không tồn tại cho email: " + customerEmail));
        //2. Lấy lịch sử đặt bàn của user đang đăng nhập
        Page<Reservation> reservations = reservationRepository.findByCustomer(profile, pageable);
        return reservations.map(this::mapToResponse);
    }

    //Hàm helper để map từ Entity sang DTO
    private ReservationResponse mapToResponse(Reservation reservation) {
        return new ReservationResponse(
            reservation.getId(),
            reservation.getRestaurant().getId(),
            reservation.getRestaurant().getName(),
            reservation.getReservationDate(),
            reservation.getReservationTime(),
            reservation.getGuestCount(),
            reservation.getNotes(),
            reservation.getDepositAmount(),
            reservation.getStatus()
        );
    }
}
