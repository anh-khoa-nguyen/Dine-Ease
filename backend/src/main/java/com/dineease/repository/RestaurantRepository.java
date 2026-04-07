package com.dineease.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Restaurant;
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findByOwnerEmail(String email);
}