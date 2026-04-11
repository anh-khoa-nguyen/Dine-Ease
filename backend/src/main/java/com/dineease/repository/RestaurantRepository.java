package com.dineease.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Restaurant;
import com.dineease.entity.RestaurantStatus;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findByStatus (RestaurantStatus status, Pageable pageable); //Tìm quán đang hoạt động để hiển thị cho khách hàng
}