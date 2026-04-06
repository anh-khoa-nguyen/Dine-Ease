package com.dineease.dto;

import jakarta.validation.constraints.NotBlank;

public record CuisineRequest(
    @NotBlank(message = "Tên danh mục không được để trống")
    String name,
    
    String iconUrl
){}