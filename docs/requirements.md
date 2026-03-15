# Phân Tích Yêu Cầu & Đặc Tả Use Case - Hệ Thống Đặt Bàn Nhà Hàng

## 1. Phân Tích Yêu Cầu (Requirements Analysis)
Hiện nay, việc đặt bàn tại các nhà hàng thường diễn ra thủ công qua điện thoại hoặc đến trực tiếp. Khách hàng gặp khó khăn trong việc tìm kiếm không gian phù hợp, không biết trước thực đơn, giá cả và tình trạng bàn trống. Ngược lại, các nhà hàng cũng chật vật với việc quản lý lịch hẹn bằng sổ sách, khó sắp xếp sơ đồ bàn tối ưu và thường xuyên chịu thiệt hại do tình trạng khách đặt nhưng không đến (no-show). Để giải quyết triệt để vấn đề này, **"Hệ Thống Đặt Bàn Nhà Hàng Trực Tuyến"** được xây dựng nhằm tạo ra một nền tảng (platform) số hóa, làm cầu nối trực tiếp giữa thực khách và các cơ sở kinh doanh dịch vụ F&B.

Hệ thống được thiết kế để mang lại lợi ích toàn diện cho cả 3 nhóm đối tượng tham gia. Đối với **Khách hàng**, nền tảng mang đến trải nghiệm tiện lợi khi cho phép tìm kiếm nhà hàng theo vị trí/loại ẩm thực (cuisine), tham khảo menu, đặt chỗ 24/7 và thanh toán tiền cọc an toàn qua cổng điện tử (VNPay/Momo). Đối với **Nhà hàng**, hệ thống cung cấp bộ công cụ quản trị mạnh mẽ giúp số hóa thực đơn, theo dõi sơ đồ bàn trực quan theo thời gian thực, quản lý đơn hàng và tự động hóa quy trình xác nhận. Cuối cùng, đối với **Ban quản trị (Admin)**, hệ thống cung cấp các công cụ kiểm duyệt đối tác chặt chẽ, cấu hình mức chiết khấu (commission) linh hoạt và hệ thống báo cáo thống kê tổng quan để theo dõi, định hướng sự phát triển của toàn bộ nền tảng.

### 1.1. Đối tượng sử dụng (Actors)
Hệ thống bao gồm 3 phân hệ người dùng chính:
*   **Khách hàng (End User):** Người dùng cuối tìm kiếm nhà hàng, xem menu, đặt bàn và đánh giá.
*   **Nhà hàng (Business User):** Quản lý thông tin gian hàng, thực đơn, sơ đồ bàn và xử lý đơn đặt bàn.
*   **Admin (Quản trị hệ thống):** Quản lý toàn bộ hoạt động của nền tảng, duyệt nhà hàng, cấu hình hệ thống và xem báo cáo.
*   **Actor phụ (Hệ thống ngoài):** Cổng thanh toán (VNPay/Momo), Hệ thống gửi Email, Dịch vụ lưu trữ ảnh (Cloudinary/S3).

---

## 2. Sơ Đồ Use Case Tổng Quát (General Use Case Diagram)
![Sơ đồ Use Case Tổng quát](./screenshots/usecase-general.png)

---

## 3. Đặc Tả Use Case & Wireframes Theo Từng Phân Hệ

### 3.1. Phân hệ Khách hàng (Customer)
#### 3.1.1. Sơ đồ Use Case Khách hàng
![Sơ đồ Use Case Khách hàng](./screenshots/usecase-customer.png)

#### 3.1.2. Chi tiết Đặc tả & Wireframes
**1. UC_CUSTOMER_01: Tìm kiếm nhà hàng**
[Paste bảng đặc tả UC_CUSTOMER_01 vào đây]

*Wireframe tương ứng:*
![Wireframe Tìm kiếm nhà hàng](./screenshots/wireframe-customer-search.png)
*(Mô tả ngắn gọn wireframe: Màn hình hiển thị thanh tìm kiếm, bộ lọc cuisine, danh sách kết quả dạng card...)*

**2. UC_CUSTOMER_02: Xem menu và giá**
[Paste bảng đặc tả UC_CUSTOMER_02 vào đây]

*Wireframe tương ứng:*
![Wireframe Xem menu](./screenshots/wireframe-customer-menu.png)

**3. UC_CUSTOMER_03: Đánh giá nhà hàng**
[Paste bảng đặc tả UC_CUSTOMER_03 vào đây]

*Wireframe tương ứng:*
![Wireframe Đánh giá](./screenshots/wireframe-customer-review.png)

**4. UC_CUSTOMER_04: Xem lịch sử đặt bàn**
[Paste bảng đặc tả UC_CUSTOMER_04 vào đây]

*Wireframe tương ứng:*
![Wireframe Lịch sử](./screenshots/wireframe-customer-history.png)

**5. UC_CUSTOMER_05: Đặt bàn online**
[Paste bảng đặc tả UC_CUSTOMER_05 vào đây]

*Wireframe tương ứng:*
![Wireframe Đặt bàn](./screenshots/wireframe-customer-booking.png)

**6. UC_CUSTOMER_06: Thanh toán đặt cọc**
[Paste bảng đặc tả UC_CUSTOMER_06 vào đây]

*Wireframe tương ứng:*
![Wireframe Thanh toán](./screenshots/wireframe-customer-payment.png)

---

### 3.2. Phân hệ Quản trị viên (Admin)
#### 3.2.1. Sơ đồ Use Case Admin
![Sơ đồ Use Case Admin](./screenshots/usecase-admin.png)

#### 3.2.2. Chi tiết Đặc tả & Wireframes
**1. UC_ADMIN_01: Duyệt nhà hàng mới**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_01 |
| **Name**<br>(Tên use case) | Duyệt nhà hàng mới |
| **Description**<br>(Mô tả) | Use case này mô tả quy trình Quản trị viên (Admin) xem xét yêu cầu đăng ký mở gian hàng từ một đối tác Nhà hàng mới. Admin sẽ kiểm tra các thông tin pháp lý, hình ảnh, thực đơn dự kiến và đưa ra quyết định Phê duyệt (kèm thiết lập mức hoa hồng), Từ chối, hoặc Yêu cầu bổ sung thông tin. |
| **Primary Actor**<br>(Actor chính) | Admin (Quản trị hệ thống) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống gửi Email (Ví dụ: JavaMail / SendGrid) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị (Admin Dashboard).<br>2. Có ít nhất một yêu cầu đăng ký nhà hàng mới đang ở trạng thái chờ duyệt (`PENDING`) trong cơ sở dữ liệu. |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. Trạng thái của nhà hàng được cập nhật thành `APPROVED` (Đã duyệt) hoặc `REJECTED` (Từ chối) trong CSDL.<br>2. Mức chiết khấu (commission) được lưu lại (nếu duyệt).<br>3. Một email thông báo kết quả được gửi tự động đến chủ nhà hàng.<br><br>**Thất bại:**<br>1. Trạng thái của nhà hàng không thay đổi.<br>2. Hệ thống hiển thị thông báo lỗi cụ thể cho Admin. |
| **Main flows**<br>(Luồng hoạt động chính) | 1. Use case bắt đầu khi Admin chọn mục "Quản lý đối tác" -> "Yêu cầu chờ duyệt" trên thanh điều hướng.<br>2. Hệ thống truy vấn CSDL và hiển thị danh sách các nhà hàng đang ở trạng thái `PENDING`.<br>3. Admin nhấn vào nút "Xem chi tiết" tại một nhà hàng cụ thể.<br>4. Hệ thống hiển thị giao diện chi tiết hồ sơ nhà hàng bao gồm: Thông tin chủ sở hữu, Giấy phép kinh doanh (file ảnh/PDF), Địa chỉ, Hình ảnh không gian, và Thực đơn dự kiến.<br>5. Admin kiểm tra tính hợp lệ của các thông tin và tài liệu.<br>6. Admin nhấn nút **"Phê duyệt"**.<br>7. Hệ thống hiển thị một Dialog (hộp thoại) yêu cầu Admin nhập **Mức hoa hồng (Commission %)** áp dụng cho nhà hàng này và xác nhận.<br>8. Admin nhập mức hoa hồng (ví dụ: 15%) và nhấn "Xác nhận duyệt".<br>9. Hệ thống gửi yêu cầu cập nhật (POST/PUT) xuống Backend.<br>10. Backend cập nhật trạng thái nhà hàng thành `APPROVED` và lưu mức hoa hồng vào CSDL.<br>11. Backend kích hoạt một tác vụ nền (background task) gọi đến Hệ thống gửi Email để gửi thư chúc mừng và hướng dẫn đăng nhập cho Chủ nhà hàng.<br>12. Hệ thống hiển thị thông báo "Đã phê duyệt nhà hàng thành công" và đưa Admin quay lại danh sách chờ duyệt (Bước 2).<br>13. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế) | **6a. Admin quyết định Từ chối nhà hàng:**<br>1. Tại bước 6, Admin nhấn nút **"Từ chối"**.<br>2. Hệ thống hiển thị Dialog yêu cầu nhập "Lý do từ chối" (Bắt buộc).<br>3. Admin nhập lý do (ví dụ: Giấy phép kinh doanh mờ, không hợp lệ) và xác nhận.<br>4. Backend cập nhật trạng thái thành `REJECTED` và lưu lý do vào CSDL.<br>5. Backend gọi Hệ thống gửi Email để gửi thư thông báo từ chối kèm lý do cho Chủ nhà hàng.<br>6. Luồng hoạt động tiếp tục tại Bước 12.<br><br>**6b. Admin Yêu cầu bổ sung thông tin:**<br>1. Tại bước 6, Admin nhấn nút **"Yêu cầu bổ sung"**.<br>2. Hệ thống hiển thị Dialog yêu cầu nhập "Nội dung cần bổ sung".<br>3. Admin nhập nội dung (ví dụ: Cập nhật lại ảnh mặt tiền nhà hàng) và xác nhận.<br>4. Backend cập nhật trạng thái thành `UPDATE_REQUIRED`.<br>5. Backend gọi Hệ thống gửi Email thông báo cho Chủ nhà hàng vào cập nhật lại hồ sơ.<br>6. Luồng hoạt động tiếp tục tại Bước 12. |
| **Exception flows**<br>(Luồng ngoại lệ) | **10a. Lỗi kết nối Cơ sở dữ liệu (Database Error):**<br>1. Tại bước 10 (hoặc bước 4 của luồng 6a/6b), nếu CSDL gặp sự cố (timeout, deadlock).<br>2. Backend bắt lỗi (catch exception), hủy bỏ giao dịch (rollback) để đảm bảo tính toàn vẹn dữ liệu.<br>3. Backend trả về mã lỗi 500 Internal Server Error.<br>4. Hệ thống hiển thị thông báo: *"Lỗi hệ thống: Không thể cập nhật trạng thái lúc này. Vui lòng thử lại sau."*<br>5. Use case kết thúc trong trạng thái thất bại.<br><br>**11a. Lỗi Hệ thống gửi Email (Email Service Down):**<br>1. Tại bước 11, tác vụ nền không thể kết nối với máy chủ SMTP (JavaMail lỗi).<br>2. Backend ghi log lỗi hệ thống (Error Logging) và đưa tác vụ gửi email vào hàng đợi để thử lại sau (Retry Queue).<br>3. Trạng thái nhà hàng **vẫn được lưu thành công** trong CSDL.<br>4. Hệ thống hiển thị thông báo cảnh báo (Warning) cho Admin: *"Đã duyệt nhà hàng thành công, nhưng hệ thống gửi email đang gián đoạn. Email sẽ được gửi lại sau."*<br>5. Luồng hoạt động tiếp tục tại Bước 12. |

*Wireframe tương ứng:*
![Wireframe Duyệt nhà hàng](./screenshots/wireframe-admin-approve.png)

**2. UC_ADMIN_02: Quản lý nhà hàng (CRUD)**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_02 |
| **Name**<br>(Tên use case) | Quản lý nhà hàng (CRUD) |
| **Description**<br>(Mô tả) | Use case này cho phép Quản trị viên (Admin) xem danh sách toàn bộ nhà hàng đang hoạt động trên nền tảng. Admin có thể tìm kiếm, lọc, xem chi tiết, thêm mới nhà hàng thủ công, cập nhật thông tin (trạng thái hoạt động, mức hoa hồng) và khóa/xóa tài khoản nhà hàng khi có vi phạm. |
| **Primary Actor**<br>(Actor chính) | Admin (Quản trị hệ thống) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống Cơ sở dữ liệu (PostgreSQL/MySQL) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị (Admin Dashboard).<br>2. Admin có quyền (Role/Permission) truy cập module "Quản lý nhà hàng". |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. Các thay đổi (Thêm/Sửa/Xóa) được lưu trữ thành công vào CSDL.<br>2. Giao diện danh sách nhà hàng được cập nhật dữ liệu mới nhất.<br><br>**Thất bại:**<br>1. CSDL không bị thay đổi (Rollback nếu có lỗi).<br>2. Hệ thống hiển thị thông báo lỗi rõ ràng cho Admin. |
| **Main flows**<br>(Luồng hoạt động chính) | 1. Use case bắt đầu khi Admin chọn mục "Quản lý Nhà hàng" trên thanh menu điều hướng.<br>2. Hệ thống truy vấn CSDL và hiển thị danh sách tất cả các nhà hàng (phân trang), bao gồm các thông tin cơ bản: Tên, Chủ sở hữu, Trạng thái (Active/Inactive), Mức hoa hồng.<br>3. Admin nhấn vào nút **"Chỉnh sửa"** (hoặc Xem chi tiết) tại một nhà hàng cụ thể.<br>4. Hệ thống hiển thị form chi tiết chứa toàn bộ thông tin hiện tại của nhà hàng đó.<br>5. Admin tiến hành thay đổi thông tin (Ví dụ: Chỉnh sửa mức hoa hồng từ 15% lên 20%, hoặc thay đổi số điện thoại liên hệ).<br>6. Admin nhấn nút **"Lưu thay đổi"**.<br>7. Hệ thống (Frontend) kiểm tra tính hợp lệ của dữ liệu vừa nhập (Validate form).<br>8. Ứng dụng gửi yêu cầu (PUT/PATCH) kèm dữ liệu xuống Backend.<br>9. Backend cập nhật bản ghi tương ứng trong CSDL.<br>10. Hệ thống hiển thị thông báo "Cập nhật thông tin nhà hàng thành công" và quay lại màn hình danh sách (Bước 2).<br>11. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế) | **2a. Tìm kiếm và Lọc nhà hàng (Read):**<br>1. Tại bước 2, Admin nhập từ khóa (Tên nhà hàng) vào ô tìm kiếm hoặc chọn bộ lọc (Trạng thái: Đang hoạt động, Cuisine: Món Âu).<br>2. Hệ thống truy vấn lại CSDL dựa trên tham số lọc và cập nhật lại danh sách hiển thị.<br>3. Luồng hoạt động tiếp tục tại Bước 3.<br><br>**2b. Thêm mới nhà hàng thủ công (Create):**<br>1. Tại bước 2, Admin nhấn nút **"Thêm nhà hàng mới"**.<br>2. Hệ thống hiển thị một form trống.<br>3. Admin điền các thông tin bắt buộc (Tên, Địa chỉ, SĐT, Mức hoa hồng...).<br>4. Admin nhấn "Lưu".<br>5. Backend tạo bản ghi mới trong CSDL và tự động tạo tài khoản đăng nhập (Business User) cho chủ nhà hàng.<br>6. Luồng hoạt động tiếp tục tại Bước 10.<br><br>**3a. Khóa/Xóa nhà hàng (Delete/Deactivate):**<br>1. Tại bước 3, Admin nhấn nút **"Khóa/Vô hiệu hóa"** (Soft delete) tại một nhà hàng vi phạm.<br>2. Hệ thống hiển thị Dialog xác nhận: *"Bạn có chắc chắn muốn vô hiệu hóa nhà hàng này? Các đặt bàn sắp tới có thể bị ảnh hưởng."*<br>3. Admin nhấn "Xác nhận".<br>4. Backend cập nhật trạng thái nhà hàng thành `INACTIVE` (Không xóa cứng để giữ lịch sử dữ liệu).<br>5. Luồng hoạt động tiếp tục tại Bước 10. |
| **Exception flows**<br>(Luồng ngoại lệ) | **7a. Dữ liệu không hợp lệ từ phía Backend (Validation Error):**<br>1. Tại bước 8 (hoặc bước 4 của luồng 2b), nếu Admin nhập dữ liệu vi phạm ràng buộc CSDL (Ví dụ: Trùng số điện thoại đã tồn tại, hoặc mức hoa hồng nhập chữ thay vì số) mà Frontend lọt lưới.<br>2. Backend từ chối lưu và trả về mã lỗi `400 Bad Request`.<br>3. Hệ thống hiển thị thông báo lỗi màu đỏ ngay dưới trường dữ liệu bị sai (VD: *"Số điện thoại này đã được đăng ký cho nhà hàng khác"*).<br>4. Admin sửa lại dữ liệu và nhấn "Lưu" lần nữa.<br><br>**9a. Xung đột dữ liệu đồng thời (Concurrent Update Conflict):**<br>1. Tại bước 9, nếu có một Admin khác cũng đang chỉnh sửa và vừa lưu thành công thông tin của **cùng một nhà hàng** đó trước tích tắc.<br>2. Backend phát hiện phiên bản dữ liệu không khớp (Optimistic Locking).<br>3. Backend trả về mã lỗi `409 Conflict`.<br>4. Hệ thống hiển thị thông báo: *"Dữ liệu nhà hàng này vừa được cập nhật bởi một Quản trị viên khác. Vui lòng tải lại trang để xem thông tin mới nhất trước khi chỉnh sửa."*<br>5. Use case kết thúc thất bại, Admin phải F5 lại trang.<br><br>**9b. Mất kết nối Cơ sở dữ liệu:**<br>1. Tại bước 9, hệ thống không thể kết nối tới PostgreSQL/MySQL.<br>2. Backend trả về lỗi `500 Internal Server Error`.<br>3. Hệ thống hiển thị thông báo popup: *"Lỗi máy chủ: Không thể lưu dữ liệu lúc này. Vui lòng thử lại sau."* |

*Wireframe tương ứng:*
![Wireframe Quản lý nhà hàng](./screenshots/wireframe-admin-restaurant-crud.png)

**3. UC_ADMIN_03: Quản lý danh mục cuisine**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_03 |
| **Name**<br>(Tên use case) | Quản lý danh mục cuisine (CRUD) |
| **Description**<br>(Mô tả) | Use case này cho phép Quản trị viên (Admin) quản lý các danh mục ẩm thực (Cuisine Categories) trên hệ thống. Admin có thể xem danh sách, tìm kiếm, thêm mới, chỉnh sửa tên/hình ảnh đại diện, hoặc xóa các danh mục. Các danh mục này được dùng để nhà hàng tự phân loại và khách hàng sử dụng làm bộ lọc tìm kiếm. |
| **Primary Actor**<br>(Actor chính) | Admin (Quản trị hệ thống) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống Cơ sở dữ liệu (PostgreSQL/MySQL)<br>Dịch vụ lưu trữ hình ảnh (Ví dụ: AWS S3 / Cloudinary - *nếu có upload icon*) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị.<br>2. Admin có quyền truy cập module "Cấu hình hệ thống" -> "Danh mục Cuisine". |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. CSDL được cập nhật với các danh mục cuisine mới hoặc đã chỉnh sửa.<br>2. Ứng dụng của Khách hàng và Nhà hàng lập tức nhận được danh sách danh mục mới nhất.<br><br>**Thất bại:**<br>1. Trạng thái CSDL giữ nguyên.<br>2. Hệ thống hiển thị thông báo lỗi giải thích lý do không thể lưu/xóa. |
| **Main flows**<br>(Luồng hoạt động chính) | 1. Use case bắt đầu khi Admin chọn "Danh mục Cuisine" trên menu quản trị.<br>2. Hệ thống truy vấn CSDL và hiển thị danh sách các danh mục hiện có (Tên danh mục, Icon/Hình ảnh, Số lượng nhà hàng đang sử dụng).<br>3. Admin nhấn nút **"Chỉnh sửa"** tại một danh mục cụ thể (Ví dụ: "Món Nhật").<br>4. Hệ thống hiển thị form chứa thông tin hiện tại của danh mục.<br>5. Admin thay đổi thông tin (Ví dụ: Đổi tên thành "Ẩm thực Nhật Bản", tải lên icon mới).<br>6. Admin nhấn nút **"Lưu thay đổi"**.<br>7. Hệ thống (Frontend) kiểm tra tính hợp lệ (không được để trống tên).<br>8. Ứng dụng gửi yêu cầu (PUT/PATCH) xuống Backend.<br>9. Backend cập nhật bản ghi trong CSDL (và upload icon mới lên Cloudinary nếu có).<br>10. Hệ thống hiển thị thông báo: *"Cập nhật danh mục thành công"* và tải lại danh sách (Bước 2).<br>11. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế) | **2a. Thêm mới danh mục (Create):**<br>1. Tại bước 2, Admin nhấn nút **"Thêm danh mục mới"**.<br>2. Hệ thống hiển thị form trống.<br>3. Admin nhập Tên danh mục (VD: "Đồ ăn kiêng") và tải lên Icon.<br>4. Admin nhấn "Lưu".<br>5. Backend tạo bản ghi mới trong CSDL.<br>6. Luồng hoạt động tiếp tục tại Bước 10.<br><br>**3a. Xóa danh mục (Delete):**<br>1. Tại bước 3, Admin nhấn nút **"Xóa"** tại một danh mục **chưa có nhà hàng nào sử dụng** (Số lượng = 0).<br>2. Hệ thống hiển thị Dialog: *"Bạn có chắc chắn muốn xóa danh mục này vĩnh viễn?"*.<br>3. Admin nhấn "Xác nhận".<br>4. Backend thực hiện lệnh DELETE trong CSDL.<br>5. Luồng hoạt động tiếp tục tại Bước 10. |
| **Exception flows**<br>(Luồng ngoại lệ) | **3b. Lỗi Ràng buộc Khóa ngoại (Foreign Key Constraint) khi Xóa:**<br>1. Tại bước 3 của luồng 3a, Admin cố tình nhấn "Xóa" một danh mục **đang có nhà hàng sử dụng** (VD: Xóa "Món Việt" đang có 50 nhà hàng gắn tag).<br>2. Backend thực hiện lệnh DELETE nhưng bị CSDL từ chối do vi phạm khóa ngoại (Cuisine_ID đang tồn tại trong bảng Restaurant).<br>3. Backend bắt lỗi (catch exception) và trả về mã `409 Conflict` hoặc `400 Bad Request`.<br>4. Hệ thống hiển thị thông báo lỗi màu đỏ: *"Không thể xóa! Danh mục này đang được sử dụng bởi 50 nhà hàng. Vui lòng chuyển đổi danh mục cho các nhà hàng đó trước khi xóa."*<br>5. Use case kết thúc thất bại.<br><br>**8a. Lỗi Trùng lặp tên danh mục (Unique Constraint):**<br>1. Tại bước 8 (hoặc bước 4 của luồng 2a), Admin nhập tên danh mục đã tồn tại trong hệ thống (VD: Tạo mới danh mục "Hải sản" trong khi đã có "Hải sản").<br>2. Backend kiểm tra CSDL và phát hiện vi phạm ràng buộc Unique.<br>3. Backend trả về lỗi.<br>4. Hệ thống hiển thị cảnh báo: *"Tên danh mục này đã tồn tại. Vui lòng chọn tên khác."*<br>5. Admin phải sửa lại tên và thử lưu lại.<br><br>**9a. Lỗi dịch vụ lưu trữ ảnh (Cloudinary/S3 Down):**<br>1. Tại bước 9, Admin có tải lên Icon mới nhưng dịch vụ lưu trữ ảnh bên thứ 3 bị lỗi timeout.<br>2. Backend hủy bỏ giao dịch lưu CSDL (Rollback) để tránh việc có data nhưng mất ảnh.<br>3. Hệ thống thông báo: *"Lỗi tải ảnh lên máy chủ. Vui lòng kiểm tra lại file ảnh hoặc thử lại sau."* |

*Wireframe tương ứng:*
![Wireframe Quản lý danh mục](./screenshots/wireframe-admin-category.png)

**4. UC_ADMIN_04: Cấu hình commission**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_04 |
| **Name**<br>(Tên use case) | Cấu hình commission (Mức hoa hồng) |
| **Description**<br>(Mô tả) | Use case này cho phép Quản trị viên cấp cao (Super Admin) thiết lập và thay đổi mức phần trăm hoa hồng (Commission Rate) mặc định mà nền tảng sẽ thu từ các nhà hàng trên mỗi đơn đặt bàn thành công. Hệ thống sẽ lưu lại lịch sử thay đổi để phục vụ đối soát doanh thu. Mức cấu hình này sẽ áp dụng cho các nhà hàng mới đăng ký hoặc các nhà hàng đang sử dụng mức mặc định. |
| **Primary Actor**<br>(Actor chính) | Super Admin (Quản trị viên cấp cao) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống Cơ sở dữ liệu (PostgreSQL/MySQL) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị.<br>2. Admin phải có quyền hạn cao nhất (`ROLE_SUPER_ADMIN`) mới được phép truy cập module "Cấu hình tài chính". |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. Mức hoa hồng mặc định mới được lưu vào CSDL (Bảng cấu hình hệ thống).<br>2. Một bản ghi lịch sử thay đổi (Audit Log) được tạo ra ghi rõ người thay đổi, thời gian, mức cũ và mức mới.<br>3. Các đơn đặt bàn phát sinh *sau thời điểm này* (đối với nhà hàng dùng mức mặc định) sẽ áp dụng mức hoa hồng mới.<br><br>**Thất bại:**<br>1. Trạng thái CSDL không thay đổi.<br>2. Hệ thống hiển thị thông báo lỗi giải thích lý do không thể lưu. |
| **Main flows**<br>(Luồng hoạt động chính) | 1. Use case bắt đầu khi Super Admin chọn "Cấu hình hệ thống" -> "Cấu hình Hoa hồng (Commission)" trên menu quản trị.<br>2. Hệ thống truy vấn CSDL và hiển thị Mức hoa hồng mặc định hiện tại (Ví dụ: 15%) cùng với Bảng lịch sử các lần thay đổi trước đó.<br>3. Super Admin nhấn nút **"Cập nhật mức hoa hồng"**.<br>4. Hệ thống hiển thị một Dialog (hộp thoại) yêu cầu nhập mức phần trăm mới.<br>5. Super Admin nhập giá trị mới (Ví dụ: 18%) và bắt buộc phải nhập "Lý do thay đổi" (Ví dụ: "Điều chỉnh chính sách năm 2026").<br>6. Super Admin nhấn nút **"Lưu cấu hình"**.<br>7. Hệ thống (Frontend) kiểm tra tính hợp lệ của dữ liệu (Giá trị phải là số dương, nằm trong khoảng 0 - 100, lý do không được để trống).<br>8. Ứng dụng gửi yêu cầu (PUT/POST) kèm dữ liệu xuống Backend.<br>9. Backend thực hiện một giao dịch CSDL (Database Transaction) bao gồm 2 bước:<br>&nbsp;&nbsp;&nbsp;a. Cập nhật bản ghi cấu hình hệ thống với mức hoa hồng mới (18%).<br>&nbsp;&nbsp;&nbsp;b. Tạo một bản ghi mới trong bảng Lịch sử (Audit Log) lưu lại: ID của Admin, Thời gian, Mức cũ (15%), Mức mới (18%), Lý do.<br>10. Backend trả về phản hồi thành công.<br>11. Hệ thống hiển thị thông báo: *"Cập nhật mức hoa hồng mặc định thành công"* và tải lại trang hiển thị mức mới cùng dòng lịch sử vừa thêm (Bước 2).<br>12. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế - *Nghiệp vụ hợp lệ*) | **5a. Hủy bỏ thao tác:**<br>1. Tại bước 5, Super Admin quyết định không thay đổi nữa và nhấn nút **"Hủy"** hoặc đóng Dialog.<br>2. Hệ thống đóng hộp thoại, không gửi yêu cầu nào xuống Backend.<br>3. Use case kết thúc.<br><br>**2a. Xem chi tiết lịch sử thay đổi:**<br>1. Tại bước 2, Super Admin muốn xem ai đã thay đổi mức hoa hồng vào tháng trước.<br>2. Super Admin sử dụng bộ lọc thời gian hoặc phân trang trên Bảng lịch sử.<br>3. Hệ thống truy vấn CSDL và hiển thị danh sách lịch sử tương ứng.<br>4. Luồng hoạt động tiếp tục tại Bước 3. |
| **Exception flows**<br>(Luồng ngoại lệ - *Ràng buộc DB, Phân quyền & Lỗi hệ thống*) | **8a. Lỗi Phân quyền (Unauthorized Access):**<br>1. Tại bước 8, nếu một Admin thông thường (không phải Super Admin) cố tình gửi yêu cầu API cập nhật hoa hồng (có thể qua Postman hoặc lỗi hiển thị UI).<br>2. Backend kiểm tra Token (JWT) và phát hiện user không có quyền `ROLE_SUPER_ADMIN`.<br>3. Backend từ chối yêu cầu và trả về mã lỗi `403 Forbidden`.<br>4. Hệ thống hiển thị thông báo lỗi màu đỏ: *"Bạn không có quyền thực hiện thao tác này. Vui lòng liên hệ Quản trị viên cấp cao."*<br>5. Use case kết thúc thất bại.<br><br>**9a. Lỗi Giao dịch CSDL (Transaction Rollback):**<br>1. Tại bước 9, quá trình lưu cấu hình mới (bước 9a) thành công nhưng quá trình ghi log lịch sử (bước 9b) bị lỗi (do mất kết nối DB hoặc lỗi ràng buộc bảng log).<br>2. Nhờ cơ chế `transaction.atomic` (hoặc tương đương), Backend tự động hủy bỏ (Rollback) toàn bộ giao dịch. Mức hoa hồng sẽ quay về giá trị cũ (15%).<br>3. Backend trả về mã lỗi `500 Internal Server Error`.<br>4. Hệ thống hiển thị thông báo: *"Lỗi hệ thống: Không thể lưu cấu hình lúc này để đảm bảo tính toàn vẹn dữ liệu. Vui lòng thử lại sau."*<br>5. Use case kết thúc thất bại.<br><br>**7a. Lỗi Dữ liệu không hợp lệ (Validation Error):**<br>1. Tại bước 7, Super Admin nhập giá trị hoa hồng là "-5" hoặc "120".<br>2. Frontend phát hiện lỗi validation ngay lập tức (hoặc Backend trả về lỗi `400 Bad Request` nếu lọt qua Frontend).<br>3. Hệ thống chặn thao tác lưu và hiển thị cảnh báo dưới ô nhập liệu: *"Mức hoa hồng phải là số dương và không vượt quá 100%."*<br>4. Super Admin phải nhập lại giá trị hợp lệ. |

*Wireframe tương ứng:*
![Wireframe Cấu hình hoa hồng](./screenshots/wireframe-admin-commission.png)

**5. UC_ADMIN_05: Báo cáo toàn hệ thống**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_05 |
| **Name**<br>(Tên use case) | Báo cáo toàn hệ thống (Dashboard & Export) |
| **Description**<br>(Mô tả) | Use case này cho phép Quản trị viên (Admin) xem bức tranh tổng quan về hoạt động kinh doanh của toàn bộ nền tảng. Admin có thể xem các chỉ số (Tổng doanh thu hoa hồng, Tổng số lượt đặt bàn, Top nhà hàng nổi bật...), lọc dữ liệu theo khoảng thời gian tùy chỉnh và xuất báo cáo ra file (Excel/PDF) để lưu trữ hoặc phân tích thêm. |
| **Primary Actor**<br>(Actor chính) | Admin (Quản trị hệ thống) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống Cơ sở dữ liệu (PostgreSQL/MySQL)<br>Thư viện tạo file (Ví dụ: Apache POI cho Excel, iText/PDFBox cho PDF) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị.<br>2. Admin có quyền truy cập module "Báo cáo & Thống kê". |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. Admin xem được các biểu đồ/số liệu thống kê chính xác trên màn hình.<br>2. File báo cáo (Excel/PDF) được tạo thành công và tải xuống máy tính của Admin.<br><br>**Thất bại:**<br>1. Hệ thống không hiển thị được dữ liệu hoặc không tạo được file.<br>2. Hệ thống hiển thị thông báo lỗi (VD: Dữ liệu quá lớn, Lỗi máy chủ). |
| **Main flows**<br>(Luồng hoạt động chính - *Xem và Lọc dữ liệu*) | 1. Use case bắt đầu khi Admin chọn mục "Báo cáo thống kê" trên thanh menu điều hướng.<br>2. Hệ thống mặc định gửi yêu cầu lấy dữ liệu thống kê của **Tháng hiện tại**.<br>3. Backend truy vấn CSDL, tính toán tổng hợp (SUM, COUNT, GROUP BY) và trả về dữ liệu dạng JSON.<br>4. Hệ thống (Frontend) hiển thị Dashboard bao gồm:<br>&nbsp;&nbsp;&nbsp;- Các thẻ số liệu (Tổng doanh thu, Tổng đơn đặt bàn thành công/hủy).<br>&nbsp;&nbsp;&nbsp;- Biểu đồ đường (Line chart) thể hiện xu hướng đặt bàn theo ngày.<br>&nbsp;&nbsp;&nbsp;- Danh sách "Top 5 nhà hàng có doanh thu cao nhất".<br>5. Admin chọn một khoảng thời gian khác (Ví dụ: "Từ 01/01/2026 đến 31/03/2026") và nhấn nút **"Lọc dữ liệu"**.<br>6. Hệ thống gửi yêu cầu mới với tham số `start_date` và `end_date` xuống Backend.<br>7. Backend tính toán lại và trả về kết quả mới.<br>8. Giao diện Dashboard cập nhật lại các biểu đồ và con số tương ứng với Quý 1/2026.<br>9. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế - *Xuất file và Dữ liệu rỗng*) | **4a. Xuất báo cáo ra file (Export):**<br>1. Tại bước 4 (hoặc bước 8), Admin nhấn nút **"Xuất báo cáo"** và chọn định dạng **"Excel"**.<br>2. Hệ thống gửi yêu cầu Export xuống Backend kèm theo khoảng thời gian đang lọc.<br>3. Backend truy vấn dữ liệu chi tiết và sử dụng thư viện để ghi dữ liệu vào file `.xlsx`.<br>4. Backend trả về luồng dữ liệu file (File Stream) hoặc URL tải file.<br>5. Trình duyệt của Admin tự động tải file Excel xuống máy tính.<br>6. Use case kết thúc.<br><br>**7a. Không có dữ liệu trong khoảng thời gian đã chọn:**<br>1. Tại bước 7, Backend truy vấn nhưng không có bất kỳ giao dịch đặt bàn nào trong khoảng thời gian Admin chọn (VD: Chọn ngày trong tương lai).<br>2. Backend trả về mảng dữ liệu rỗng `[]` và các chỉ số bằng `0`.<br>3. Hệ thống hiển thị thông báo thân thiện trên Dashboard: *"Không có dữ liệu giao dịch nào trong khoảng thời gian này."* và ẩn các biểu đồ.<br>4. Luồng hoạt động quay lại Bước 5 (Chờ Admin chọn ngày khác). |
| **Exception flows**<br>(Luồng ngoại lệ - *Lỗi hiệu năng và Hệ thống*) | **6a. Lỗi khoảng thời gian quá lớn (Validation/Performance Constraint):**<br>1. Tại bước 6, Admin cố tình chọn khoảng thời gian quá dài (Ví dụ: Lọc dữ liệu của 5 năm qua).<br>2. Backend kiểm tra logic (Validation) và nhận thấy khoảng thời gian vượt quá giới hạn cho phép truy vấn trực tiếp (VD: Giới hạn tối đa là 1 năm/lần lọc để tránh treo DB).<br>3. Backend từ chối truy vấn và trả về mã lỗi `400 Bad Request`.<br>4. Hệ thống hiển thị cảnh báo: *"Khoảng thời gian lọc quá lớn. Vui lòng chọn khoảng thời gian tối đa là 12 tháng để đảm bảo tốc độ tải dữ liệu."*<br>5. Admin phải chọn lại ngày.<br><br>**7b. Lỗi Quá thời gian truy vấn (Database Timeout):**<br>1. Tại bước 7, do lượng dữ liệu trong CSDL quá khổng lồ (hàng triệu đơn đặt bàn), câu lệnh `GROUP BY` chạy quá lâu vượt mức cho phép (VD: > 30 giây).<br>2. CSDL tự động ngắt kết nối (Timeout) để giải phóng tài nguyên.<br>3. Backend trả về mã lỗi `504 Gateway Timeout`.<br>4. Hệ thống hiển thị thông báo: *"Hệ thống đang quá tải do lượng dữ liệu lớn. Vui lòng thử lại sau hoặc thu hẹp khoảng thời gian lọc."*<br><br>**3a (Luồng Export). Lỗi tạo file báo cáo (Out of Memory / File Generation Error):**<br>1. Tại bước 3 của luồng 4a, khi Backend đang tạo file Excel với hàng trăm ngàn dòng, máy chủ bị hết RAM (Out of Memory) hoặc thư viện lỗi.<br>2. Backend bắt lỗi (catch exception) và trả về mã `500 Internal Server Error`.<br>3. Hệ thống hiển thị thông báo: *"Đã xảy ra lỗi trong quá trình tạo file Excel. Vui lòng thử lại hoặc liên hệ bộ phận kỹ thuật."* |

*Wireframe tương ứng:*
![Wireframe Báo cáo Admin](./screenshots/wireframe-admin-report.png)

**6. UC_ADMIN_06: Quản lý thông báo**
| Trường thông tin | Nội dung |
| :--- | :--- |
| **Use case Id** | UC_ADMIN_06 |
| **Name**<br>(Tên use case) | Quản lý thông báo (Tạo và Gửi thông báo) |
| **Description**<br>(Mô tả) | Use case này cho phép Quản trị viên (Admin) soạn thảo và gửi các thông báo hệ thống (System Notifications) đến người dùng. Admin có thể chọn đối tượng nhận (Tất cả, Chỉ Khách hàng, Chỉ Nhà hàng, hoặc một User cụ thể), lên lịch gửi hoặc gửi ngay lập tức. Thông báo có thể được gửi qua App (Push Notification/In-app) hoặc Email. |
| **Primary Actor**<br>(Actor chính) | Admin (Quản trị hệ thống) |
| **Secondary Actor**<br>(Actor phụ) | Hệ thống Hàng đợi Tác vụ nền (Ví dụ: Celery + Redis)<br>Dịch vụ gửi thông báo (Ví dụ: Firebase Cloud Messaging - FCM / JavaMail) |
| **Pre-conditions**<br>(Tiền điều kiện) | 1. Admin đã đăng nhập thành công vào hệ thống quản trị.<br>2. Admin có quyền truy cập module "Quản lý Thông báo". |
| **Post-conditions**<br>(Hậu điều kiện) | **Thành công:**<br>1. Một bản ghi "Chiến dịch thông báo" (Notification Campaign) được lưu vào CSDL với trạng thái `SENT` (Đã gửi) hoặc `SCHEDULED` (Đã lên lịch).<br>2. Tác vụ gửi thông báo được đẩy vào Hàng đợi (Queue) thành công.<br><br>**Thất bại:**<br>1. Không có thông báo nào được gửi đi.<br>2. Hệ thống hiển thị lỗi rõ ràng cho Admin. |
| **Main flows**<br>(Luồng hoạt động chính - *Gửi thông báo ngay lập tức*) | 1. Use case bắt đầu khi Admin chọn "Quản lý Thông báo" -> "Tạo thông báo mới" trên menu.<br>2. Hệ thống hiển thị Form soạn thảo thông báo bao gồm: Tiêu đề, Nội dung, Đối tượng nhận (Tất cả/Khách hàng/Nhà hàng/Cá nhân), và Kênh gửi (App/Email).<br>3. Admin điền Tiêu đề (VD: "Bảo trì hệ thống đêm nay") và Nội dung chi tiết.<br>4. Admin chọn Đối tượng nhận là "Tất cả Nhà hàng" và Kênh gửi là "In-app Notification".<br>5. Admin nhấn nút **"Gửi thông báo"**.<br>6. Hệ thống (Frontend) kiểm tra tính hợp lệ (Tiêu đề/Nội dung không được rỗng).<br>7. Hệ thống hiển thị Dialog xác nhận: *"Bạn chuẩn bị gửi thông báo này đến [Số lượng] Nhà hàng. Xác nhận gửi?"*<br>8. Admin nhấn "Đồng ý".<br>9. Ứng dụng gửi payload (POST) xuống Backend.<br>10. Backend lưu bản ghi thông báo vào CSDL với trạng thái `PROCESSING` (Đang xử lý).<br>11. Backend đẩy tác vụ (Task) chứa nội dung và danh sách ID người nhận vào **Hàng đợi (Redis/Celery)** để xử lý ngầm (Background processing).<br>12. Backend trả về phản hồi thành công ngay lập tức cho Admin.<br>13. Hệ thống hiển thị thông báo: *"Thông báo đang được gửi đi trong nền."* và chuyển Admin về màn hình Lịch sử thông báo.<br>14. (Ngầm) Worker (Celery) lấy task từ hàng đợi, gọi API của Firebase (FCM) để đẩy thông báo đến các thiết bị, sau đó cập nhật trạng thái trong CSDL thành `SENT`.<br>15. Use case kết thúc. |
| **Alternative flows**<br>(Luồng thay thế - *Lên lịch và Xem lịch sử*) | **4a. Lên lịch gửi thông báo (Scheduled):**<br>1. Tại bước 4, Admin chọn thêm tùy chọn "Lên lịch gửi" và chọn thời gian (VD: 08:00 sáng mai).<br>2. Tại bước 10, Backend lưu bản ghi với trạng thái `SCHEDULED` (Đã lên lịch).<br>3. Tại bước 11, Backend đẩy tác vụ vào Hàng đợi có hẹn giờ (Scheduled Task - VD: Celery Beat).<br>4. Luồng hoạt động tiếp tục tại Bước 12.<br><br>**1a. Xem Lịch sử thông báo:**<br>1. Tại bước 1, Admin chọn "Lịch sử thông báo".<br>2. Hệ thống truy vấn CSDL và hiển thị danh sách các thông báo đã tạo, bao gồm Trạng thái (`SENT`, `PROCESSING`, `FAILED`, `SCHEDULED`) và Số lượng người đã nhận/đọc.<br>3. Admin có thể nhấn "Xem chi tiết" hoặc "Hủy" (đối với các thông báo đang `SCHEDULED`). |
| **Exception flows**<br>(Luồng ngoại lệ - *Lỗi Hàng đợi và Dịch vụ bên thứ 3*) | **11a. Lỗi kết nối Hàng đợi Tác vụ (Redis/Message Broker Down):**<br>1. Tại bước 11, Backend cố gắng đẩy tác vụ gửi thông báo vào Redis nhưng Redis bị sập (Connection Refused).<br>2. Backend bắt lỗi, cập nhật trạng thái bản ghi thông báo trong CSDL thành `FAILED` (Thất bại).<br>3. Backend trả về mã lỗi `500 Internal Server Error`.<br>4. Hệ thống hiển thị thông báo lỗi màu đỏ: *"Lỗi hệ thống: Không thể khởi tạo tiến trình gửi thông báo. Vui lòng liên hệ kỹ thuật."*<br>5. Use case kết thúc thất bại.<br><br>**14a. Lỗi Dịch vụ Firebase/Email (FCM/SMTP Error) trong lúc chạy ngầm:**<br>1. Tại bước 14 (tiến trình chạy ngầm), Worker đang gửi thông báo thì dịch vụ Firebase (FCM) báo lỗi (VD: Quá giới hạn API rate limit, hoặc Token thiết bị hết hạn).<br>2. Worker ghi log lỗi cụ thể cho từng User bị thất bại.<br>3. Nếu lỗi toàn hệ thống (FCM sập), Worker sẽ đưa task trở lại Hàng đợi để thử lại sau (Retry mechanism).<br>4. Nếu thử lại quá số lần quy định (Max retries), Worker cập nhật trạng thái chiến dịch trong CSDL thành `PARTIALLY_SENT` (Gửi một phần) hoặc `FAILED`.<br>*(Lưu ý: Vì đây là tiến trình ngầm, Admin sẽ không thấy lỗi ngay lập tức trên màn hình, mà chỉ thấy trạng thái `FAILED` khi xem lại Lịch sử thông báo ở luồng 1a).* |

*Wireframe tương ứng:*
![Wireframe Quản lý thông báo](./screenshots/wireframe-admin-notification.png)

---

### 3.3. Phân hệ Nhà hàng (Restaurant / Business User)
#### 3.3.1. Sơ đồ Use Case Nhà hàng
![Sơ đồ Use Case Nhà hàng](./screenshots/usecase-restaurant.png)

#### 3.3.2. Chi tiết Đặc tả & Wireframes
**1. UC_RESTAURANT_01: Quản lý thông tin nhà hàng**
[Paste bảng đặc tả UC_RESTAURANT_01 vào đây]

*Wireframe tương ứng:*
![Wireframe Hồ sơ nhà hàng](./screenshots/wireframe-rest-profile.png)

**2. UC_RESTAURANT_02: Quản lý thực đơn**
[Paste bảng đặc tả UC_RESTAURANT_02 vào đây]

*Wireframe tương ứng:*
![Wireframe Quản lý thực đơn](./screenshots/wireframe-rest-menu.png)

**3. UC_RESTAURANT_03: Quản lý bàn (Sơ đồ bàn)**
[Paste bảng đặc tả UC_RESTAURANT_03 vào đây]

*Wireframe tương ứng:*
![Wireframe Sơ đồ bàn](./screenshots/wireframe-rest-tables.png)

**4. UC_RESTAURANT_04: Quản lý thanh toán**
[Paste bảng đặc tả UC_RESTAURANT_04 vào đây]

*Wireframe tương ứng:*
![Wireframe Thanh toán POS](./screenshots/wireframe-rest-payment.png)

**5. UC_RESTAURANT_05: Báo cáo doanh thu**
[Paste bảng đặc tả UC_RESTAURANT_05 vào đây]

*Wireframe tương ứng:*
![Wireframe Báo cáo doanh thu](./screenshots/wireframe-rest-report.png)