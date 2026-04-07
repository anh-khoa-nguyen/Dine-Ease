package com.dineease.service;
import com.dineease.dto.TableRequest;
import com.dineease.dto.TableResponse;
import com.dineease.entity.Restaurant;
import com.dineease.entity.RestaurantTable;
import com.dineease.entity.TableStatus;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.RestaurantRepository;
import com.dineease.repository.RestaurantTableRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageTableService {
    private final RestaurantTableRepository tableRepository;
    private final RestaurantRepository restaurantRepository;

    public TableResponse createTable(TableRequest request, String email) {
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Nhà hàng không tồn tại"));

        RestaurantTable table = RestaurantTable.builder()
            .tableName(request.tableName()) 
            .capacity(request.capacity())
            .restaurant(restaurant)
            .status(TableStatus.AVAILABLE)
            .build();

        RestaurantTable saved = tableRepository.save(table);
        return new TableResponse(saved.getId(), saved.getTableName(), saved.getCapacity(), saved.getStatus());
    }

    public List<TableResponse> getTablesByRestaurant(String email) {
        return tableRepository.findByRestaurantOwnerEmail(email).stream()
            .map(t -> new TableResponse(t.getId(), t.getTableName(), t.getCapacity(), t.getStatus()))
            .toList();
    }
}