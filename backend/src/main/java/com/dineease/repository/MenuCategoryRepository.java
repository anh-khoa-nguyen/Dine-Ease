package com.dineease.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.MenuCategory;
public interface MenuCategoryRepository extends JpaRepository<MenuCategory, Long>{
    
}