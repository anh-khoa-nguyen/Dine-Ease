package com.dineease.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dineease.dto.MenuCategoryPublicResponse;
import com.dineease.dto.MenuItemPublicResponse;
import com.dineease.dto.RestaurantPublicResponse;
import com.dineease.entity.MenuCategory;
import com.dineease.entity.MenuItem;
import com.dineease.entity.Restaurant;
import com.dineease.entity.RestaurantStatus;
import com.dineease.exception.ResourceNotFoundException;
import com.dineease.repository.MenuItemRepository;
import com.dineease.repository.RestaurantRepository;



//Phục vụ cho khách vãng lai
@Service
public class PublicDiscoveryService {
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    public PublicDiscoveryService(RestaurantRepository restaurantRepository, MenuItemRepository menuItemRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
    }

    //Lấy danh sách quán ăn đang hoạt động để hiển thị cho khách hàng
    @Transactional(readOnly = true)
    public Page<RestaurantPublicResponse> getAllPublicRestaurants(Pageable pageable) {
        Page<Restaurant> restaurants = restaurantRepository.findByStatus(RestaurantStatus.ACTIVE, pageable);
        return restaurants.map(r -> new RestaurantPublicResponse(
            r.getId(),
            r.getName(),
            r.getAddress(),
            r.getImageMain(),
            r.getAvgRating()
        ));
    }

    //Lấy menu của một quán ăn cụ thể, gom nhóm theo danh mục
    @Transactional(readOnly = true)
    public List<MenuCategoryPublicResponse> getRestaurantMenu(Long restaurantId) {
        //Kiểm tra đầu tiên xem quán có tồn tại hay k
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new ResourceNotFoundException("Nhà hàng", restaurantId);
        }

        List<MenuItem> allItems = menuItemRepository.findByRestaurantId(restaurantId);
        // DÙNG JAVA STREAM ĐỂ GOM NHÓM: Phân loại món ăn theo Danh mục (Category)
        Map<MenuCategory, List<MenuItem>> groupedMenu = allItems.stream().collect(Collectors.groupingBy(MenuItem::getCategory));
        // Chuyển đổi Map sang List<MenuCategoryPublicResponse> trả về cho Frontend
        return groupedMenu.entrySet().stream()
                .map(entry -> {
                    MenuCategory category = entry.getKey();
                    List<MenuItem> itemsInCategory = entry.getValue();
                    // Map List<Entity> sang List<DTO>
                    List<MenuItemPublicResponse> itemDtos = itemsInCategory.stream()
                        .map(item -> new MenuItemPublicResponse(
                            item.getId(),
                            item.getName(),
                            item.getDescription(),
                            item.getPrice(),
                            item.getImageUrl(),
                            item.getIsBestseller()
                        )).collect(Collectors.toList());

                    return new MenuCategoryPublicResponse(
                        category.getId(),
                        category.getName(),
                        itemDtos
                    );
                })
                .collect(Collectors.toList());
    }
}