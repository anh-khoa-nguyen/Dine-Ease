package com.dineease.dto;
import com.dineease.entity.MenuItemStatus;
public record MenuItemResponse(
    Long id,
    String name,
    String description,
    Double price,
    String imageUrl, 
    Boolean isBestseller,
    MenuItemStatus status,
    String categoryName 
) {}