package com.dineease.dto;

//Hiển thị món ăn cho khách xem
public record MenuItemPublicResponse(
    Long id,
    String name,
    String description,
    Double price,
    String imageUrl,
    Boolean isBestseller
) {}
