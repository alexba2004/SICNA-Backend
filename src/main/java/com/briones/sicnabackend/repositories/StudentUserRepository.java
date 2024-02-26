package com.briones.sicnabackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.briones.sicnabackend.models.StudentUser;

public interface StudentUserRepository extends JpaRepository<StudentUser, Long> {
    Optional<StudentUser> findByStudentId(Long studentId);
}
