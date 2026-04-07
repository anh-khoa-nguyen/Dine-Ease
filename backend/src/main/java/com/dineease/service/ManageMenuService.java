package com.dineease.service;
import com.dineease.dto.MenuItemRequest;
import com.dineease.dto.MenuItemResponse;
import com.dineease.entity.MenuCategory;
import com.dineease.entity.MenuItem;
import com.dineease.entity.MenuItemStatus;
import com.dineease.entity.Restaurant;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.MenuCategoryRepository;
import com.dineease.repository.MenuItemRepository;
import com.dineease.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.security.access.AccessDeniedException;

@Service
@RequiredArgsConstructor
@Transactional
public class ManageMenuService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuCategoryRepository categoryRepository;
    
    public MenuItemResponse createMenuItem(MenuItemRequest request, String email) {
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("Bạn chưa đăng ký nhà hàng!"));

        MenuCategory category = categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));

        MenuItem item = MenuItem.builder()
            .name(request.name())
            .description(request.description())
            .price(request.price())
            .category(category)
            .restaurant(restaurant)
            .status(MenuItemStatus.AVAILABLE) 
            .isBestseller(false)
            .build();

        return mapToResponse(menuItemRepository.save(item));
    }

    public List<MenuItemResponse> getMenuItemsByRestaurant(String email) {
        return menuItemRepository.findByRestaurantOwnerEmail(email).stream()
                .map(this::mapToResponse)
                .toList();
    }

    public MenuItemResponse updateMenuItem(Long itemId, MenuItemRequest request, String email) {
        MenuItem item = menuItemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Món ăn không tồn tại"));

        if (!item.getRestaurant().getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Bạn không có quyền sửa món ăn này");
        }

        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        
        return mapToResponse(menuItemRepository.save(item));
    }

    public void deleteMenuItem(Long itemId, String email) {
        MenuItem item = menuItemRepository.findById(itemId)
            .orElseThrow(() -> new ResourceNotFoundException("Món ăn không tồn tại"));

        if (!item.getRestaurant().getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Bạn không có quyền xóa món ăn này");
        }

        menuItemRepository.delete(item);
    }

    private MenuItemResponse mapToResponse(MenuItem item) {
        return new MenuItemResponse(
            item.getId(),
            item.getName(),
            item.getDescription(),
            item.getPrice(),
            item.getImageUrl(),
            item.getIsBestseller(),
            item.getStatus(),
            item.getCategory().getName()
        );
    }
}