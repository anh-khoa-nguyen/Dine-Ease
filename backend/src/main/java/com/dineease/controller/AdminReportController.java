package com.dineease.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dineease.dto.AdminDashboardResponse;
import com.dineease.service.AdminReportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Admin - Reports & Statistics", description = "Quản trị viên: Xem báo cáo, thống kê doanh thu")
@RestController
@RequestMapping("/api/v1/admin/reports")
@SecurityRequirement(name = "bearerAuth")
public class AdminReportController {

    private final AdminReportService adminReportService;

    public AdminReportController(AdminReportService adminReportService) {
        this.adminReportService = adminReportService;
    }

    @Operation(summary = "Lấy dữ liệu tổng quan cho Dashboard", 
               description = "Trả về tổng nhà hàng, tổng đơn, số đơn thành công và tổng hoa hồng.")
    @GetMapping("/dashboard")
    public ResponseEntity<AdminDashboardResponse> getDashboardStats() {
        AdminDashboardResponse response = adminReportService.getDashboardStats();
        return ResponseEntity.ok(response);
    }
}