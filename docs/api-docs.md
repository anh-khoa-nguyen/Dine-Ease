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

### 2.1. Nhóm Auth & Profile (Xác thực & Hồ sơ)
*Controller gợi ý: `AuthController.java`, `CustomerProfileController.java`*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **POST** | `/auth/register` | Public | **Body:** `{ "email": "", "password": "", "full_name": "", "phone": "" }`<br>*(Sử dụng `@Valid` để check NotBlank, Email format)* |
| **POST** | `/auth/login` | Public | **Body:** `{ "email": "", "password": "" }`<br>**Response:** `{ "accessToken": "...", "tokenType": "Bearer" }` |
| **GET** | `/customers/me` | `ROLE_CUSTOMER`| Lấy thông tin cá nhân hiện tại (từ Token).<br>**Response:** Tên, SĐT, Avatar, `loyalty_points`, `total_bookings`. |
| **PUT** | `/customers/me` | `ROLE_CUSTOMER`| **Body:** `{ "full_name": "...", "phone": "..." }` |
| **POST** | `/customers/me/avatar` | `ROLE_CUSTOMER`| **Params:** `@RequestPart("file") MultipartFile file`<br>*(Upload ảnh lên Cloudinary, trả về URL mới)* |

--- 
### 2.2. Nhóm Discovery (Khám phá & Tìm kiếm Nhà hàng)
*Controller gợi ý: `PublicRestaurantController.java`. Nhóm này hoàn toàn **Public**, dùng để khách lướt xem app khi chưa đăng nhập.*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **GET** | `/cuisines` | Public | Lấy danh sách danh mục ẩm thực (Dùng làm bộ lọc). |
| **GET** | `/restaurants` | Public | **Tìm kiếm phân trang:**<br>`@RequestParam(required=false) String keyword`<br>`@RequestParam(required=false) Long cuisineId`<br>`@RequestParam(defaultValue="rating_desc") String sortBy`<br>`Pageable pageable` (Thay cho page/size) |
| **GET** | `/restaurants/{id}` | Public | **Chi tiết quán:**<br>`@PathVariable Long id`<br>*(Trả về kèm `OperatingHours`, `RestaurantImages` và `RestaurantConfig`)* |
| **GET** | `/restaurants/{id}/menu`| Public | Lấy thực đơn (Đã gom nhóm theo `MenuCategory`).<br>`@PathVariable Long id` |
| **GET** | `/restaurants/{id}/reviews`| Public | Lấy đánh giá của quán.<br>`@PathVariable Long id`, `Pageable pageable` |

--- 
### 2.3. Nhóm Reservation (Đặt bàn & Lịch sử)
*Controller gợi ý: `ReservationController.java`. Trái tim của phân hệ Khách hàng.*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **GET** | `/restaurants/{id}/availability` | Public / Private | **Kiểm tra bàn trống:** (Nên tách ra API GET riêng để frontend gọi realtime khi khách đổi giờ).<br>`@PathVariable Long id`<br>`@RequestParam @DateTimeFormat(iso=DATE) LocalDate date`<br>`@RequestParam @DateTimeFormat(iso=TIME) LocalTime time`<br>`@RequestParam int guestCount` |
| **POST** | `/reservations` | `ROLE_CUSTOMER`| **Tạo đơn đặt bàn mới:**<br>**Body (ReservationRequestDTO):**<br>`{ "restaurant_id": 1, "reservation_date": "2026-10-20", "reservation_time": "19:00:00", "guest_count": 4, "notes": "Gần cửa sổ" }`<br>**Response:** Mã đơn, Status, và `deposit_amount` (Nếu > 0 thì frontend chuyển hướng sang trang thanh toán). |
| **GET** | `/reservations` | `ROLE_CUSTOMER`| **Lịch sử đặt bàn của tôi:**<br>`@RequestParam(required=false) String status` (UPCOMING / HISTORY)<br>`Pageable pageable` |
| **GET** | `/reservations/{id}` | `ROLE_CUSTOMER`| **Chi tiết 1 đơn:**<br>`@PathVariable Long id` *(Backend bắt buộc check `id` này có thuộc về `customer_id` trong Token không).* |
| **PATCH**| `/reservations/{id}/cancel` | `ROLE_CUSTOMER`| **Khách tự hủy bàn:**<br>`@PathVariable Long id`<br>**Body:** `{ "cancel_reason": "Bận đột xuất" }` |

---

### 2.4. Nhóm Payment (Thanh toán cọc qua Ví điện tử)
*Controller gợi ý: `PaymentController.java`*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **POST** | `/reservations/{id}/payment-url`| `ROLE_CUSTOMER`| **Tạo Link thanh toán Momo/VNPay:**<br>`@PathVariable Long id`<br>**Body:** `{ "payment_method": "MOMO", "voucher_code": "GIAM50K" }`<br>**Response:** `{ "paymentUrl": "https://momo.vn/..." }` |
| **GET** | `/reservations/{id}/payment-status`| `ROLE_CUSTOMER`| **Polling check trạng thái:** Frontend gọi liên tục 3s/lần sau khi redirect về app để xem thanh toán đã SUCCESS chưa. |
| **POST** | `/payments/webhook/{provider}`| **Public** | **Webhook (Server-to-Server):** VNPay/Momo gọi vào đây.<br>`@PathVariable String provider` (momo / vnpay)<br>`@RequestBody String payload` (Lấy thô để check HmacSHA256 Signature). |

---
### 2.5. Nhóm Review (Đánh giá)
*Controller gợi ý: `ReviewController.java`. Chỉ được đánh giá khi đơn đã COMPLETED.*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **POST** | `/reservations/{id}/reviews` | `ROLE_CUSTOMER`| **Đăng đánh giá:**<br>`@PathVariable Long id`<br>**Body (Multipart/form-data):**<br>`rating` (int 1-5)<br>`comment` (String)<br>`images` (List<MultipartFile> - tùy chọn). | 

### 2.6. Nhóm In-App Notifications (Thông báo)
*Controller gợi ý: `NotificationController.java`*

| Method | Endpoint | Security | Spring Boot Params & Body (DTO) |
| :--- | :--- | :--- | :--- |
| **GET** | `/notifications` | `ROLE_CUSTOMER`| **Danh sách quả chuông:**<br>`@RequestParam(defaultValue="false") boolean unreadOnly` (Chỉ lấy chưa đọc)<br>`Pageable pageable` |
| **PATCH**| `/notifications/{id}/read` | `ROLE_CUSTOMER`| **Đánh dấu 1 thông báo đã đọc:**<br>`@PathVariable Long id` |
| **PATCH**| `/notifications/read-all` | `ROLE_CUSTOMER`| **Đánh dấu đọc tất cả:** Update toàn bộ `is_read = true` cho user hiện tại. |

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