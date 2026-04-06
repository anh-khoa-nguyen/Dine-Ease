package com.dineease.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.RestaurantTable;
public interface RestaurantTableRepository extends JpaRepository<RestaurantTable,Long> {

}