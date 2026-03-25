# 📚 API Design - Hệ Thống Đặt Bàn Nhà Hàng (Restaurant Booking)

Tài liệu này định nghĩa toàn bộ RESTful APIs cho 3 phân hệ: Khách hàng, Nhà hàng và Quản trị viên.

---

## 1. Quy Ước

### 1.1. Base URL
Tất cả các API đều bắt đầu bằng:
`http://localhost:8080/api/v1`

### 1.2. Xác Thực
Trừ các API được đánh dấu **Public**, tất cả các API khác đều yêu cầu truyền JWT Token vào Header:
`Authorization: Bearer <your_jwt_token>`

### 1.3. Response
**Thành công (200 OK / 201 Created):**
```json
{
  "status": 200,
  "message": "Success",
  "data": { ... } // Dữ liệu trả về nằm ở đây
}
```

**Thất bại (400 / 401 / 403 / 404 / 500):**
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Dữ liệu đầu vào không hợp lệ",
  "timestamp": "2026-10-20T19:00:00Z"
}
```

---

## 2. Phân Hệ Khách Hàng (End User)
*Lưu ý: Các API không có tiền tố role. Backend tự động lấy `customer_id` từ Token.*

### 2.1.


### 2.2. 


### 2.3. 


### 2.4. 


### 2.5. 


---

## 3. Phân Hệ Nhà Hàng (Business User)
*Lưu ý: Bắt buộc Token có quyền `ROLE_RESTAURANT`. Backend tự động lấy `restaurant_id` từ Profile của nhân viên.*

### 3.1. 


### 3.2. 


### 3.3. 


### 3.4. 


### 3.5. 


---

## 4. Phân Hệ Quản Trị (Admin)
*Lưu ý: Yêu cầu quyền `ROLE_ADMIN` hoặc `ROLE_SUPER_ADMIN`.*

### 4.1. Nhóm Quản lý & Duyệt Nhà Hàng

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/restaurants` | **Lấy ds nhà hàng (Dùng cho cả màn Quản lý & Chờ duyệt):**<br>`@RequestParam(required=false) String status` (PENDING, ACTIVE, INACTIVE...)<br>`@RequestParam(required=false) String keyword`<br>`Pageable pageable` |
| **GET** | `/admin/restaurants/{id}` | **Xem chi tiết hồ sơ để duyệt:**<br>`@PathVariable Long id`<br>*Return:* Thông tin, Giấy tờ pháp lý, Menu dự kiến. |
| **PATCH**| `/admin/restaurants/{id}/approve` | **Phê duyệt nhà hàng:** Bắt buộc thiết lập mức hoa hồng.<br>`@PathVariable Long id`<br>`@Valid @RequestBody ApproveRestaurantDTO` *(commission_rate)* |
| **PATCH**| `/admin/restaurants/{id}/reject` | **Từ chối nhà hàng:**<br>`@PathVariable Long id`<br>`@Valid @RequestBody RejectRestaurantDTO` *(reason)* |
| **PATCH**| `/admin/restaurants/{id}/request-update`| **Yêu cầu cập nhật lại hồ sơ:**<br>`@PathVariable Long id`<br>`@Valid @RequestBody UpdateRequestDTO` *(message)* |
| **POST** | `/admin/restaurants` | **Tạo mới nhà hàng thủ công:**<br>`@Valid @RequestBody RestaurantCreateDTO`<br>*(owner_email, name, address, phone, commission_rate)* |
| **PUT** | `/admin/restaurants/{id}` | **Chỉnh sửa thông tin/Mức hoa hồng của 1 quán:**<br>`@PathVariable Long id`<br>`@Valid @RequestBody RestaurantUpdateDTO` |
| **PATCH**| `/admin/restaurants/{id}/status` | **Bật/Tắt hoạt động (Khóa quán vi phạm):**<br>`@PathVariable Long id`<br>`@RequestBody Map<String, String> body` *(status)* |

### 4.2. Nhóm Quản lý Danh mục Cuisine

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/cuisines` | **Lấy danh sách Danh mục:**<br>*Return:* `List<CuisineAdminDTO>` *(Kèm theo count_restaurant đang sử dụng)*. |
| **POST** | `/admin/cuisines` | **Thêm danh mục mới (Upload Icon):**<br>`@RequestPart("name") String name`<br>`@RequestPart("icon") MultipartFile iconFile` |
| **PUT** | `/admin/cuisines/{id}` | **Cập nhật danh mục:**<br>`@PathVariable Long id`<br>`@RequestPart("name") String name`<br>`@RequestPart(value="icon", required=false) MultipartFile iconFile` |
| **DELETE**| `/admin/cuisines/{id}` | **Xóa danh mục:**<br>`@PathVariable Long id`<br>*Lưu ý: Backend phải check ràng buộc khóa ngoại (FK) trước khi xóa.* |

### 4.3. Nhóm Cấu hình Hệ thống & Audit Log

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/configs` | **Lấy ds cấu hình hiện tại:** (Mức hoa hồng mặc định, v.v...). |
| **PUT** | `/admin/configs/{configKey}` | **Cập nhật cấu hình:**<br>`@PathVariable String configKey`<br>`@Valid @RequestBody ConfigUpdateDTO`<br>*(config_value, reason)*<br>*Backend tự động tạo 1 bản ghi vào bảng `audit_logs` dựa trên `reason` này.* |
| **GET** | `/admin/audit-logs` | **Xem lịch sử thay đổi (Audit Trail):**<br>`@RequestParam(required=false) String tableName`<br>`@RequestParam(required=false) String adminName`<br>`Pageable pageable` |

### 4.4. Nhóm Quản lý Người dùng

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/users` | **Lấy danh sách người dùng:**<br>`@RequestParam(required=false) String role` (CUSTOMER, RESTAURANT)<br>`@RequestParam(required=false) String keyword`<br>`Pageable pageable` |
| **PATCH**| `/admin/users/{id}/status` | **Khóa / Mở khóa tài khoản (Ban User):**<br>`@PathVariable Long id`<br>`@RequestBody Map<String, String> body` *(status: BANNED/ACTIVE)* |

### 4.5. Nhóm Báo cáo & Thống kê

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/reports/dashboard` | **Thẻ KPI (Tổng quan):**<br>`@RequestParam @DateTimeFormat(iso=DATE) LocalDate startDate`<br>`@RequestParam @DateTimeFormat(iso=DATE) LocalDate endDate`<br>*Return:* Tổng hoa hồng, Số đơn thành công, Số quán active. |
| **GET** | `/admin/reports/revenue-chart` | **Dữ liệu Biểu đồ đường:**<br>*Params tương tự trên.*<br>*Return:* `List<ChartDataDTO>` (date, commission_revenue, total_bookings) |
| **GET** | `/admin/reports/top-restaurants`| **Bảng xếp hạng:**<br>*Params tương tự trên.*<br>`@RequestParam String criteria` (REVENUE_DESC, CANCEL_DESC) |
| **GET** | `/admin/reports/export` | **Xuất báo cáo Excel:**<br>*Params tương tự trên.*<br>*Return:* Trả về HTTP Response kiểu `application/vnd.openxmlformats-officedocument.spreadsheetml.sheet` (Byte stream). |

### 4.6. Nhóm Quản lý Thông báo

| Method | Endpoint | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- |
| **GET** | `/admin/notifications/campaigns`| **Lấy lịch sử các chiến dịch thông báo:**<br>`Pageable pageable` |
| **POST** | `/admin/notifications/campaigns`| **Tạo & Lên lịch gửi thông báo hàng loạt:**<br>`@Valid @RequestBody CampaignRequestDTO`<br>*{ title, content, target_audience (ALL/CUSTOMER/RESTAURANT), channel (IN_APP/EMAIL), scheduled_time (Nullable) }* |
| **GET** | `/admin/notifications/campaigns/{id}`| **Xem chi tiết 1 chiến dịch:** (Bao nhiêu người đã nhận, bao nhiêu lỗi).<br>`@PathVariable Long id` |
| **PATCH**| `/admin/notifications/campaigns/{id}/cancel`| **Hủy chiến dịch đang chờ gửi:**<br>`@PathVariable Long id`<br>*Chỉ cho phép hủy nếu status = SCHEDULED.* |

---
*(Cập nhật lần cuối: 25/03/2026)*