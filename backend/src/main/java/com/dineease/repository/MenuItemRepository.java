package com.dineease.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dineease.entity.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    @Query("SELECT m FROM MenuItem m JOIN FETCH m.category WHERE m.restaurant.id = :restaurantId")
    List<MenuItem> findByRestaurantId(@Param("restaurantId") Long restaurantId);

    List<MenuItem> findByRestaurantOwnerEmail(String email);
}