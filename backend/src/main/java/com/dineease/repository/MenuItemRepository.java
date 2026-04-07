package com.dineease.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.MenuItem;
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByRestaurantOwnerEmail(String email);

}