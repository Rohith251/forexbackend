package com.forexcard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.forexcard.dto.PendingUserDTO;
import com.forexcard.model.User;
import com.forexcard.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PutMapping("/approve/{userId}")
    public ResponseEntity<String> approveUser(@PathVariable("userId") Integer userId) {
        String result = adminService.approveUser(userId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/reject/{userId}")
    public ResponseEntity<String> denyUser(@PathVariable("userId") Integer userId) {
        String result = adminService.denyUser(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/pending")
    public ResponseEntity<List<PendingUserDTO>> getPendingUsers() {
        List<PendingUserDTO> pendingUserDTOs = adminService.getPendingUsers();
        return ResponseEntity.ok(pendingUserDTOs);
    }
}
