package com.dineease.dto;
import com.dineease.entity.TableStatus;
public record TableResponse(
    Long id,
    String tableName,
    Integer capacity,
    TableStatus status
) {}