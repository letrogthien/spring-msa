package com.letrogthien.auth.controllers;

import com.letrogthien.auth.common.RoleName;
import com.letrogthien.auth.responses.ApiResponse;
import com.letrogthien.auth.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/approve-user")
    public ApiResponse<String> approveUser(UUID userId) {
        return adminService.approveUser(userId);
    }

    @PostMapping("/reject-user")
    public ApiResponse<String> rejectUser(UUID userId) {
        return adminService.rejectUser(userId);
    }

    @PostMapping("/suspend-user")
    public ApiResponse<String> suspendUser(UUID userId) {
        return adminService.suspendUser(userId);
    }

    @PostMapping("/delete-user")
    public ApiResponse<String> deleteUser(UUID userId) {
        return adminService.deleteUser(userId);
    }

    @PostMapping("/set-role")
    public ApiResponse<String> setRoleForUser(UUID userId, RoleName roleName) {
        return adminService.setRoleForUser(userId, roleName);
    }

}
