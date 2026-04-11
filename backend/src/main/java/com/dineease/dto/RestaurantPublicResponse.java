package com.dineease.dto;

//List quán trên trang chủ
public record RestaurantPublicResponse (
    Long id,
    String name,
    String address,
    String imageMain,
    Double avgRating
) {}
