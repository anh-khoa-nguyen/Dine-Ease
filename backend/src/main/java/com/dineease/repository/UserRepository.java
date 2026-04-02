package com.dineease.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dineease.entity.Role;
import com.dineease.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    // Tìm kiếm User cho Admin Dashboard (Lọc theo Tên/Email/SĐT và Role)
    @Query("SELECT u FROM User u WHERE " + 
        "(:keyword IS NULL OR " +
        "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
        "u.phone LIKE CONCAT('%', :keyword, '%')) " +
        "AND (:role IS NULL OR u.role = :role)"
    )
    Page<User> findAllByKeywordAndRole(
        @Param("keyword") String keyword, 
        @Param("role") Role role, 
        Pageable pageable
    );
}