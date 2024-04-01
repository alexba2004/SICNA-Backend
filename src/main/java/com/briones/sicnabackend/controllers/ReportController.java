package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.report.ReportNotFoundException;
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

    // Get all reports
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }

    // Get report by id
    @GetMapping("/{id}")
    public ResponseEntity<Report> getReportById(@PathVariable Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        return reportOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + id));
    }

    // Create a new report
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        Report createdReport = reportRepository.save(report);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
    }

    // Update an existing report
    @PutMapping("/{id}")
    public ResponseEntity<Report> updateReport(@PathVariable Long id, @RequestBody Report reportDetails) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        return reportOptional.map(existingReport -> {
            existingReport.setLoanDate(reportDetails.getLoanDate());
            existingReport.setReturnDateLimit(reportDetails.getReturnDateLimit());
            existingReport.setStatus(reportDetails.getStatus());
            existingReport.setBorrowedProduct(reportDetails.getBorrowedProduct());
            existingReport.setLender(reportDetails.getLender());
            existingReport.setBorrower(reportDetails.getBorrower());
            Report updatedReport = reportRepository.save(existingReport);
            return ResponseEntity.ok(updatedReport);
        }).orElseThrow(() -> new ReportNotFoundException("Report not found with id: " + id));
    }

    // Delete a report
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long id) {
        if (reportRepository.existsById(id)) {
            reportRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new ReportNotFoundException("Report not found with id: " + id);
        }
    }
}

