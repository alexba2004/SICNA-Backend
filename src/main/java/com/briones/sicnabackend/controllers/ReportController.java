package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.report.*;
import com.briones.sicnabackend.models.Report;
import com.briones.sicnabackend.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        return reportOptional.map(ResponseEntity::ok).orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + id));
    }

    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        if (report.getId() != null) {
            throw new ReportBadRequestException("Report id must be null for creation");
        }
        Report createdReport = reportRepository.save(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            updatedReport.setId(id);
            Report savedReport = reportRepository.save(updatedReport);
            return ResponseEntity.ok(savedReport);
        } else {
            throw new ReportNotFoundException("Report not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            reportRepository.delete(reportOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new ReportNotFoundException("Report not found with id: " + id);
        }
    }
}
