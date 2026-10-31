
package com.maanas.nirbhay.controller;

import com.maanas.nirbhay.dto.AdminReportResponse;
import com.maanas.nirbhay.service.AdminService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/reports")
    public ResponseEntity<List<AdminReportResponse>> getAllReports() {

        List<AdminReportResponse> reports =
                adminService.getAllReports();

        return ResponseEntity.ok(reports);
    }
}
