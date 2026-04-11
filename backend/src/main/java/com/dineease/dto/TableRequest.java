package com.dineease.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record TableRequest(
    @NotBlank(message = "Tên/Ký hiệu bàn không được để trống")
    String tableName,
    @NotNull(message = "Sức chứa không được để trống")
    @Min(value = 1, message = "Sức chứa tối thiểu là 1 người")
    Integer capacity
) {}