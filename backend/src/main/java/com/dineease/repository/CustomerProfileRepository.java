package com.dineease.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dineease.entity.CustomerProfile;

public interface CustomerProfileRepository extends JpaRepository<CustomerProfile, Long> {
}
