package com.dineease.service;
import com.dineease.dto.ManageReservationResponse;
import com.dineease.entity.Reservation;
import com.dineease.entity.ReservationStatus;
import com.dineease.entity.RestaurantTable;
import com.dineease.entity.TableStatus;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.ReservationRepository;
import com.dineease.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManageReservationService {
    private final ReservationRepository reservationRepository;
    private final RestaurantTableRepository tableRepository;

    @Transactional(readOnly = true)
    public Page<ManageReservationResponse> getMyReservations(String email, Pageable pageable) {
        return reservationRepository.findByRestaurantOwnerEmail(email, pageable)
                .map(this::mapToResponse);
    }

    @Transactional
    public ManageReservationResponse approveReservation(Long id, String email) {
        Reservation reservation = reservationRepository.findByIdAndRestaurantOwnerEmail(id, email)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn đặt bàn không tồn tại hoặc không thuộc quyền quản lý"));

        if (reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Chỉ có thể xác nhận đơn đang ở trạng thái PENDING");
        }

        reservation.setStatus(ReservationStatus.CONFIRMED);
        return mapToResponse(reservationRepository.save(reservation));
    }

    @Transactional
    public ManageReservationResponse checkInCustomer(Long reservationId, Long tableId, String email) {
        Reservation reservation = reservationRepository.findByIdAndRestaurantOwnerEmail(reservationId, email)
                .orElseThrow(() -> new ResourceNotFoundException("Đơn đặt bàn không tồn tại"));

        if (reservation.getStatus() != ReservationStatus.CONFIRMED && reservation.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalStateException("Đơn hàng phải ở trạng thái Đã xác nhận hoặc Chờ xử lý mới có thể Check-in");
        }

        // Tìm bàn
        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Bàn không tồn tại"));

        // Chốt chặn bảo mật bàn
        if (!table.getRestaurant().getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Lỗi: Bàn này không thuộc về nhà hàng của bạn!");
        }

        // Kiểm tra bàn trống
        if (table.getStatus() != TableStatus.AVAILABLE) {
            throw new IllegalStateException("Không thể Check-in! Bàn " + table.getTableName() + " hiện không trống.");
        }

        // Cập nhật trạng thái
        table.setStatus(TableStatus.OCCUPIED);
        reservation.setStatus(ReservationStatus.CHECKED_IN);
        reservation.setAssignedTable(table);

        tableRepository.save(table);
        return mapToResponse(reservationRepository.save(reservation));
    }

    private ManageReservationResponse mapToResponse(Reservation res) {
        String customerName = res.getCustomer() != null && res.getCustomer().getUser() != null 
                ? res.getCustomer().getUser().getFullName() : "Khách ẩn danh";
        String customerPhone = res.getCustomer() != null && res.getCustomer().getUser() != null 
                ? res.getCustomer().getUser().getPhone() : "N/A";
        String tableName = res.getAssignedTable() != null ? res.getAssignedTable().getTableName() : null;

        return new ManageReservationResponse(
                res.getId(), customerName, customerPhone,
                res.getReservationDate(), res.getReservationTime(),
                res.getGuestCount(), res.getNotes(), res.getStatus(), tableName
        );
    }
}