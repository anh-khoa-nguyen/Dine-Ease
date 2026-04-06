package com.dineease.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.MenuItem;
public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    
}