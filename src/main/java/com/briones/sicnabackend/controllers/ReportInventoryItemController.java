package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.reportinventoryitem.*;
import com.briones.sicnabackend.models.ReportInventoryItem;
import com.briones.sicnabackend.repositories.ReportInventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/report-inventory-items")
public class ReportInventoryItemController {

    private final ReportInventoryItemRepository reportInventoryItemRepository;

    @Autowired
    public ReportInventoryItemController(ReportInventoryItemRepository reportInventoryItemRepository) {
        this.reportInventoryItemRepository = reportInventoryItemRepository;
    }

    @GetMapping
    public ResponseEntity<List<ReportInventoryItem>> getAllReportInventoryItems() {
        List<ReportInventoryItem> reportInventoryItems = reportInventoryItemRepository.findAll();
        return new ResponseEntity<>(reportInventoryItems, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ReportInventoryItem> createReportInventoryItem(@RequestBody ReportInventoryItem reportInventoryItem) {
        try {
            ReportInventoryItem createdReportInventoryItem = reportInventoryItemRepository.save(reportInventoryItem);
            return new ResponseEntity<>(createdReportInventoryItem, HttpStatus.CREATED);
        } catch (Exception ex) {
            throw new ReportInventoryItemBadRequestException("Error al crear el elemento de inventario de informe.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportInventoryItem> getReportInventoryItemById(@PathVariable("id") Long id) {
        ReportInventoryItem reportInventoryItem = reportInventoryItemRepository.findById(id)
                .orElseThrow(() -> new ReportInventoryItemNotFoundException("Elemento de inventario de informe no encontrado."));
        return new ResponseEntity<>(reportInventoryItem, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportInventoryItem> updateReportInventoryItem(
            @PathVariable("id") Long id, @RequestBody ReportInventoryItem reportInventoryItem) {
        if (!reportInventoryItemRepository.existsById(id)) {
            throw new ReportInventoryItemNotFoundException("Elemento de inventario de informe no encontrado.");
        }
        reportInventoryItem.setId(id);
        try {
            ReportInventoryItem updatedReportInventoryItem = reportInventoryItemRepository.save(reportInventoryItem);
            return new ResponseEntity<>(updatedReportInventoryItem, HttpStatus.OK);
        } catch (Exception ex) {
            throw new ReportInventoryItemConflictException("Error al actualizar el elemento de inventario de informe.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportInventoryItem(@PathVariable("id") Long id) {
        if (!reportInventoryItemRepository.existsById(id)) {
            throw new ReportInventoryItemNotFoundException("Elemento de inventario de informe no encontrado.");
        }
        try {
            reportInventoryItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            throw new ReportInventoryItemConflictException("Error al eliminar el elemento de inventario de informe.");
        }
    }
}
