package com.briones.sicnabackend.repositories;

import com.briones.sicnabackend.models.Report;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByBorrower_RegistrationNumber(String registrationNumber);
}

