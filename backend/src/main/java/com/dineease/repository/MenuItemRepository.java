package com.dineease.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantId(Long restaurantId); //Lấy danh sách món ăn của một quán ăn cụ thể
}