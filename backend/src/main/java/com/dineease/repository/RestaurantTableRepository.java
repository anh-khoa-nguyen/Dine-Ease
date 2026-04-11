package com.dineease.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.RestaurantTable;
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable,Long> {
    List<RestaurantTable> findByRestaurantOwnerEmail(String email);

}