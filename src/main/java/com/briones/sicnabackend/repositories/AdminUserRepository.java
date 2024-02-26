package com.briones.sicnabackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briones.sicnabackend.models.AdminUser;

public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
    Optional<AdminUser> findByEmployeeNumber(String employeeNumber);
}
