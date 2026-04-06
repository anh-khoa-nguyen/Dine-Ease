package com.dineease.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dineease.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {}