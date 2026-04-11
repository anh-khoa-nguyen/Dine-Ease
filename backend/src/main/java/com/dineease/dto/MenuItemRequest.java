package com.dineease.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record MenuItemRequest(
    @NotBlank(message = "Tên món ăn không được để trống")
    String name,
    String description,
    @NotNull(message = "Giá tiền không được để trống")
    @Positive(message = "Giá tiền phải là số dương")
    Double price,
    @NotNull(message = "Món ăn phải thuộc về một danh mục")
    Long categoryId 
) {}