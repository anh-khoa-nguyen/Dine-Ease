package com.dineease.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.CancelReservationRequest;
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
import com.dineease.repository.RestaurantTableRepository;
import com.dineease.repository.UserRepository;
//Phục vụ cho khách đặt bàn (đã đăng nhập)
@Service
public class CustomerReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantRepository restaurantRepository;
    private final CustomerProfileRepository customerProfileRepository;
    private final UserRepository userRepository;
    private final RestaurantTableRepository tableRepository;

    public CustomerReservationService(ReservationRepository reservationRepository, RestaurantRepository restaurantRepository, CustomerProfileRepository customerProfileRepository, UserRepository userRepository, RestaurantTableRepository tableRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.customerProfileRepository = customerProfileRepository;
        this.userRepository = userRepository;
        this.tableRepository = tableRepository;
    }
    // Tạo đơn đặt bàn mới
    @Transactional
    public ReservationResponse createReservation(ReservationRequest request, String customerEmail) {
        
        //Tìm restaurant theo request.restaurantId
        Restaurant restaurant = restaurantRepository.findById(request.restaurantId())
            .orElseThrow(() -> new ResourceNotFoundException("Nhà hàng", request.restaurantId()));
        
        //Kiểm tra xem nhà hàng có đủ chỗ cho số khách mà khách đặt vào ngày/giờ đó không?
        //1. Tính tổng sức chứa nhà hàng
        Integer totalCapacity = tableRepository.getTotalCapacityByRestaurantId(restaurant.getId());

        if (totalCapacity == 0 || totalCapacity == null) {
            throw new IllegalStateException("Nhà hàng hiện chưa thiết lập sơ đồ bàn, không thể nhận khách!");
        }
        //2. Tính tổng số khách đã đặt bàn tại thời điểm đặt
        Integer reservedGuests = reservationRepository.getTotalReservedGuests(
            restaurant.getId(),
            request.reservationDate(),
            request.reservationTime()
        );
        //3. Sức chứa còn lại = tổng sức chứa - số khách đã đặt
        Integer availableCapacity = totalCapacity - reservedGuests;

        //4. So sánh sức chứa còn lại với số khách mà khách đặt
        if(request.guestCount() > availableCapacity) {
            throw new IllegalStateException("Rất tiếc, nhà hàng chỉ còn chỗ cho " + availableCapacity + " khách vào thời điểm bạn chọn. Vui lòng chọn thời điểm khác hoặc giảm số lượng khách.");
        }

        //Nếu thông qua thì tìm CustomerProfile theo email
        CustomerProfile profile = customerProfileRepository.findByUserEmail(customerEmail).orElseGet(() -> {
            User user = userRepository.findByEmail(customerEmail).orElseThrow(() -> new ResourceNotFoundException("User không tồn tại"));
            CustomerProfile newProfile = CustomerProfile.builder().user(user).build();
            return customerProfileRepository.save(newProfile);
        });
        //Tạo Entity Reservation  và lưu xuống DB (Mặc định status = PENDING) - tạo đơn đặt (chưa bao gồm check kẹt bàn)

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

    //Khách hàng tự hủy bàn
    @Transactional
    public ReservationResponse cancelReservation(Long id, CancelReservationRequest request, String customerEmail) {
        //Tìm đơn đặt bàn theo ID và email của khách (Đảm bảo chỉ được hủy đơn của mình)
        Reservation reservation = reservationRepository.findByIdAndCustomerEmail(id, customerEmail)
            .orElseThrow(() -> new ResourceNotFoundException("Đơn đặt bàn không tồn tại hoặc bạn không có quyền hủy đơn này"));
        
        // Bước B: Kiểm tra logic - Chỉ cho hủy nếu chưa Check-in
        if (reservation.getStatus() == ReservationStatus.CHECKED_IN || 
            reservation.getStatus() == ReservationStatus.COMPLETE || 
            reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new IllegalArgumentException("Không thể hủy! Đơn đặt bàn này đã hoàn thành, bị hủy hoặc bạn đã tới quán.");
        }
        //Cập nhật lý do hủy và đổi status
        reservation.setCancelReason(request.cancelReason());
        reservation.setStatus(ReservationStatus.CANCELLED);

        return mapToResponse(reservationRepository.save(reservation));
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
