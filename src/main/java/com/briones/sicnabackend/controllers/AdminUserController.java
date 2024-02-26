package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.models.AdminUser;
import com.briones.sicnabackend.repositories.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin-users")
public class AdminUserController {

    @Autowired
    private AdminUserRepository adminUserRepository;

    @GetMapping
    public List<AdminUser> getAllAdminUsers() {
        return adminUserRepository.findAll();
    }
    
    @GetMapping("/{employeeNumber}")
    public ResponseEntity<AdminUser> getAdminUserByEmployeeNumber(@PathVariable Long employeeNumber) {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findByEmployeeNumber(employeeNumber);
        return adminUserOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public AdminUser createAdminUser(@RequestBody AdminUser adminUser) {
        return adminUserRepository.save(adminUser);
    }

    @PutMapping("/{employeeNumber}")
    public ResponseEntity<AdminUser> updateAdminUser(@PathVariable Long employeeNumber, @RequestBody AdminUser adminUserDetails) {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findByEmployeeNumber(employeeNumber);
        if (adminUserOptional.isPresent()) {
            AdminUser adminUser = adminUserOptional.get();
            adminUser.setName(adminUserDetails.getName());
            adminUser.setPhoneNumber(adminUserDetails.getPhoneNumber());
            adminUser.setEmail(adminUserDetails.getEmail());
            adminUser.setPassword(adminUserDetails.getPassword());
            return ResponseEntity.ok(adminUserRepository.save(adminUser));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<Void> deleteAdminUser(@PathVariable Long employeeNumber) {
        Optional<AdminUser> adminUserOptional = adminUserRepository.findByEmployeeNumber(employeeNumber);
        if (adminUserOptional.isPresent()) {
            adminUserRepository.delete(adminUserOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
