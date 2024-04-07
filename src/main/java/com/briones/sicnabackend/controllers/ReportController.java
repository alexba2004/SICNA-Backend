package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Report;
import com.briones.sicnabackend.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    // Obtener todos los reportes
    @GetMapping
    public ResponseEntity<List<Report>> getAllReports() {
        List<Report> reports = reportRepository.findAll();
        return ResponseEntity.ok(reports);
    }

    // Obtener un reporte por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getReportById(@PathVariable Long id) {
        Optional<Report> reportOptional = reportRepository.findById(id);
        if (reportOptional.isPresent()) {
            return ResponseEntity.ok(reportOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Reporte no encontrado"));
        }
    }

    // Crear un nuevo reporte
    @PostMapping
    public ResponseEntity<?> createReport(@RequestBody Report report) {
        try {
            report.setModificationDate(new Date());
            Report createdReport = reportRepository.save(report);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdReport);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el reporte");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Actualizar un reporte existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateReport(@PathVariable Long id, @RequestBody Report updatedReport) {
        try {
            Optional<Report> reportOptional = reportRepository.findById(id);
            if (reportOptional.isPresent()) {
                Report existingReport = reportOptional.get();
                existingReport.setLoanDate(updatedReport.getLoanDate());
                existingReport.setReturnDateLimit(updatedReport.getReturnDateLimit());
                existingReport.setStatus(updatedReport.getStatus());
                existingReport.setBorrowedProduct(updatedReport.getBorrowedProduct());
                existingReport.setLender(updatedReport.getLender());
                existingReport.setBorrower(updatedReport.getBorrower());
                existingReport.setModificationDate(new Date());
                Report savedReport = reportRepository.save(existingReport);
                return ResponseEntity.ok(savedReport);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Reporte no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el reporte");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar un reporte por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable Long id) {
        try {
            if (reportRepository.existsById(id)) {
                reportRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Reporte eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Reporte no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el reporte");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
