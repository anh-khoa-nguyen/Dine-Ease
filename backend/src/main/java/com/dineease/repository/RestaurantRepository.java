package com.dineease.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Restaurant;
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    
}