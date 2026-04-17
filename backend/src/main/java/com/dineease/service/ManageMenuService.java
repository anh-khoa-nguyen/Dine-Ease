package com.dineease.service;

import java.util.List;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
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
@Service
@Transactional
public class ManageMenuService {
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuCategoryRepository categoryRepository;
    private final FileUploadService fileUploadService;

    public ManageMenuService(MenuItemRepository menuItemRepository,
    RestaurantRepository restaurantRepository,
    MenuCategoryRepository categoryRepository,
    FileUploadService fileUploadService) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;
        this.categoryRepository = categoryRepository;
        this.fileUploadService = fileUploadService;
    }
    public MenuItemResponse createMenuItem(MenuItemRequest request, MultipartFile
    image, String email) {
        Restaurant restaurant = restaurantRepository.findByOwnerEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("Bạn chưa đăng ký nhà hàng!"));
        MenuCategory category = categoryRepository.findById(request.categoryId())
        .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
        // 1. Xử lý upload ảnh (nếu chủ quán có chọn ảnh)
        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = fileUploadService.uploadFile(image); // Ném lên Cloudinary
        }
        // 2. Gắn URL vào món ăn
        MenuItem item = MenuItem.builder()
        .name(request.name())
        .description(request.description())
        .price(request.price())
        .category(category)
        .restaurant(restaurant)
        .imageUrl(imageUrl) // Lưu link ảnh vào DB
        .status(MenuItemStatus.AVAILABLE)
        .isBestseller(false)
        .build();
        return mapToResponse(menuItemRepository.save(item));
    }

    @Transactional(readOnly = true)
    public List<MenuItemResponse> getMenuItemsByRestaurant(String email) {
        return menuItemRepository.findByRestaurantOwnerEmail(email).stream()
        .map(this::mapToResponse)
        .toList();
    }

    // --- CẬP NHẬT: Thêm tham số MultipartFile image ---
    public MenuItemResponse updateMenuItem(Long itemId, MenuItemRequest request,
    MultipartFile image, String email) {
        MenuItem item = menuItemRepository.findById(itemId)
        .orElseThrow(() -> new ResourceNotFoundException("Món ăn không tồn tại"));
        if (!item.getRestaurant().getOwner().getEmail().equals(email)) {
            throw new AccessDeniedException("Bạn không có quyền sửa món ăn này");
        }
        item.setName(request.name());
        item.setDescription(request.description());
        item.setPrice(request.price());
        if (!item.getCategory().getId().equals(request.categoryId())) {
            MenuCategory newCategory =
            categoryRepository.findById(request.categoryId())
            .orElseThrow(() -> new ResourceNotFoundException("Danh mục không tồn tại"));
            item.setCategory(newCategory);
        }
        // Xử lý upload ảnh mới (nếu có)
        if (image != null && !image.isEmpty()) {
            String newImageUrl = fileUploadService.uploadFile(image);
            item.setImageUrl(newImageUrl); // Đè link cũ bằng link mới
        }
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