# Đề Xuất Đề Tài Bài Tập Lớn

## 1. Thông Tin Nhóm
- Nhóm: 11
- Thành viên: 
  1. Nguyễn Anh Khoa - 2251052052 (Nhóm trưởng)
  2. Nguyễn Hoàng Yến - 2251050084
  3. Nguyễn Thiện Đoan - 2251052019
- GVHD: ThS. Võ Việt Khoa

## 2. Thông Tin Đề Tài
- Tên đề tài: Hệ Thống Đặt Bàn Nhà Hàng (Restaurant Reservation System)
- Mô tả ngắn: Platform cho phép khách hàng tìm nhà hàng theo vị trí/cuisine, xem menu và giá, đặt bàn online với thanh toán đặt cọc. Nhà hàng quản lý menu, sơ đồ bàn, xác nhận đặt và ghi nhận order.
- Đối tượng sử dụng: 
  - **Admin:** Duyệt nhà hàng mới, quản lý cuisine, báo cáo.

## 3. Tính Năng Chính (MVP)

**Phân hệ Admin:**
- [x] Duyệt nhà hàng mới
- [x] Quản lý nhà hàng (CRUD)
- [x] Quản lý danh mục cuisine
- [x] Cấu hình commission
- [x] Báo cáo toàn hệ thống
- [x] Quản lý thông báo

## 4. Công Nghệ Sử Dụng
- **Backend:** Spring Boot (Java 17+), Spring MVC (RESTful API)
- **ORM & Database:** Hibernate / Spring Data JPA, MySQL
- **Security:** Spring Security (Authentication & Authorization với JWT)
- **Frontend:** ReactJS
- **Lưu trữ file (Images):** Cloudinary API (Upload ảnh món ăn, nhà hàng)
- **Payment (nếu có):** VNPay / Momo (Mock payment)
- **Version Control:** Git + GitHub

## 5. Phân Công Công Việc (Vai trò & Trách nhiệm)

| Thành viên | Vai trò (Roles) | Phân hệ phụ trách | Tính năng nâng cao |
|------------|-----------------|-------------------|------------------------------------|
| **Nguyễn Anh Khoa**<br>*(Nhóm trưởng)* | **Project Manager (PM)**<br>Backend Dev<br>Frontend Dev | **Admin** (Quản lý nhà hàng, users, danh mục, báo cáo thống kê) | Tích hợp **Email Service** (Gửi mail thông báo khi đăng ký/đặt bàn) |

## 6. Timeline Chi Tiết & Kế Hoạch Commit (10 Tuần)

*Ghi chú: Đảm bảo 100% thành viên có ít nhất 1 commit/tuần vào GitHub theo đúng quy định.*

| Tuần | Giai đoạn | Công việc của Khoa (PM / Admin) | Công việc của Yến (QA / Khách hàng) | Công việc của Đoan (Nhà hàng) | Deliverables (Nộp GV) |
|:---:|---|---|---|---|---|
| **W1** | **Khởi động** | Setup GitHub, tạo cấu trúc thư mục, viết `README.md` (Proposal). | | | Đề xuất đề tài |
| **W2** | **Phân tích** | Vẽ Wireframes UI cho trang Admin. | | | `docs/requirements.md` |
| **W3** | **Thiết kế** | Thiết kế Database (ERD), tạo file `database-design.md`. | | | Database + API design |
| **W4** | **Backend Core** | Setup Spring Boot, cấu hình MySQL. Tạo Entities/Repos chung. | | | Setup project |
| **W5** | **Backend Core** | Viết Basic APIs (CRUD) cho Admin. | | | Backend chạy được |
| **W6** | **Backend + Auth** | Cấu hình Spring Security (Login/Register/JWT). | | | Code logic Backend |
| **W7** | **Backend + Auth** | Tích hợp **JavaMail** (Gửi email). | | | APIs hoàn chỉnh |
| **W8** | **Frontend** | Setup ReactJS, code UI/UX trang Admin, gọi API Admin. | | | Frontend cơ bản |
| **W9** | **Hoàn thiện** | **(QA/Tester)** Test chéo luồng thanh toán của Yến. Fix bugs Admin. | | | Sản phẩm hoàn chỉnh |
| **W10** | **Bảo vệ** | Viết báo cáo tổng kết, làm Slide thuyết trình. | | | Nộp bài + Bảo vệ |
## 7. Timeline
- Week 1-2: Analysis & Design
- Week 3-6: Backend Development
- Week 7-10: Frontend Development
- Week 11-12: Testing & Deployment
- Week 13-14: Documentation & Presentation