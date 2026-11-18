package com.gsdc.campushub.controller;

import com.gsdc.campushub.dto.PlatformStatsResponse;
import com.gsdc.campushub.dto.RoleUpdateRequest;
import com.gsdc.campushub.model.User;
import com.gsdc.campushub.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @PatchMapping("/users/{id}/role")
    public ResponseEntity<User> updateUserRole(@PathVariable String id, @Valid @RequestBody RoleUpdateRequest request) {
        User updated = adminService.updateUserRoleAndStatus(id, request);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/admin/stats")
    public ResponseEntity<PlatformStatsResponse> getPlatformStats() {
        return ResponseEntity.ok(adminService.getPlatformStats());
    }
}
