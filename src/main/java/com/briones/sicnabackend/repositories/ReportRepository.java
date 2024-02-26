package com.briones.sicnabackend.repositories;

import com.briones.sicnabackend.models.Report;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
