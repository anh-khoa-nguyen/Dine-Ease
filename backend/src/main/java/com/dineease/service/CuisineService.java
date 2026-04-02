package com.dineease.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.CuisineRequest;
import com.dineease.dto.CuisineResponse;
import com.dineease.entity.Cuisine;
import com.dineease.exception.DuplicateResourceException;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.CuisineRepository;

@Service
public class CuisineService {

    private final CuisineRepository cuisineRepository;

    public CuisineService(CuisineRepository cuisineRepository) {
        this.cuisineRepository = cuisineRepository;
    }

    @Transactional(readOnly = true)
    public List<CuisineResponse> getAllCuisines() {
        return cuisineRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuisineResponse getCuisineById(Long id) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục ẩm thực", id));
        return toResponse(cuisine);
    }

    @Transactional
    public CuisineResponse createCuisine(CuisineRequest request) {
        if (cuisineRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Tên danh mục ẩm thực đã tồn tại: " + request.name());
        }

        Cuisine cuisine = Cuisine.builder()
                .name(request.name())
                .iconUrl(request.iconUrl())
                .build();

        cuisine = cuisineRepository.save(cuisine);
        return toResponse(cuisine);
    }

    @Transactional
    public CuisineResponse updateCuisine(Long id, CuisineRequest request) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục ẩm thực", id));

        if (cuisineRepository.existsByNameIgnoreCaseAndIdNot(request.name(), id)) {
            throw new DuplicateResourceException("Tên danh mục ẩm thực đã tồn tại: " + request.name());
        }

        cuisine.setName(request.name());
        cuisine.setIconUrl(request.iconUrl());

        cuisine = cuisineRepository.save(cuisine);
        return toResponse(cuisine);
    }

    @Transactional
    public void deleteCuisine(Long id) {
        Cuisine cuisine = cuisineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Danh mục ẩm thực", id));
        
        // Gợi ý: Sẽ kiểm tra xem có Nhà hàng nào đang dùng Cuisine này không
        // Nếu có thì ném ra Exception (Vi phạm Khóa ngoại). Hiện tại bảng rỗng nên cứ Xóa.
        
        cuisineRepository.delete(cuisine);
    }

    // Mapper thủ công (Vì Entity này nhỏ nên không cần tạo class Mapper riêng)
    private CuisineResponse toResponse(Cuisine cuisine) {
        return new CuisineResponse(cuisine.getId(), cuisine.getName(), cuisine.getIconUrl());
    }
}