package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.models.Product;
import com.briones.sicnabackend.models.Report;
import com.briones.sicnabackend.repositories.PersonRepository;
import com.briones.sicnabackend.repositories.ProductRepository;
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
@RequestMapping("/sicna/reports")
public class ReportController {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PersonRepository personRepository; 

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

    // Obtener un reporte por su matricula
    @GetMapping("/matricula/{registrationNumber}")
    public ResponseEntity<?> getReportsByPersonRegistrationNumber(@PathVariable String registrationNumber) {
        List<Report> reports = reportRepository.findByBorrower_RegistrationNumber(registrationNumber);
        if (!reports.isEmpty()) {
            return ResponseEntity.ok(reports);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "No se encontraron reportes para la persona con el número de registro proporcionado."));
        }
    }

    // Crear un nuevo reporte
    @PostMapping
    public ResponseEntity<Report> createReport(@RequestBody Report report) {
        // Verificar si el producto prestado asociado al informe ya está administrado
        Product borrowedProduct = report.getBorrowedProduct();
        if (borrowedProduct != null && borrowedProduct.getId() != null) {
            borrowedProduct = productRepository.findById(borrowedProduct.getId()).orElse(null);
        }
        // Establecer el producto prestado en el informe
        report.setBorrowedProduct(borrowedProduct);

        // Verificar si el prestamista asociado al informe ya está administrado
        Person lender = report.getLender();
        if (lender != null && lender.getId() != null) {
            lender = personRepository.findById(lender.getId()).orElse(null);
        }
        // Establecer el prestamista en el informe
        report.setLender(lender);

        // Verificar si el prestatario asociado al informe ya está administrado
        Person borrower = report.getBorrower();
        if (borrower != null && borrower.getId() != null) {
            borrower = personRepository.findById(borrower.getId()).orElse(null);
        }
        // Establecer el prestatario en el informe
        report.setBorrower(borrower);

        // Establecer la fecha de modificación
        report.setModificationDate(new Date());

        // Guardar el informe
        Report createdReport = reportRepository.save(report);
        return new ResponseEntity<>(createdReport, HttpStatus.CREATED);
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
