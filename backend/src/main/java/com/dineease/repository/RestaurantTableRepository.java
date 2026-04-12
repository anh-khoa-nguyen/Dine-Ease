package com.dineease.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dineease.entity.RestaurantTable;
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable,Long> {
    List<RestaurantTable> findByRestaurantOwnerEmail(String email);

    // Cộng dồn tất cả số ghế (capacity) của nhà hàng. Dùng COALESCE để trả về 0 nếu chưa có bàn nào.
    @Query("SELECT COALESCE(SUM(t.capacity), 0) FROM RestaurantTable t WHERE t.restaurant.id = :restaurantId")
    Integer getTotalCapacityByRestaurantId(@Param("restaurantId") Long restaurantId);
}