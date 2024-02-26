package com.briones.sicnabackend.controllers;

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
        ReportInventoryItem createdReportInventoryItem = reportInventoryItemRepository.save(reportInventoryItem);
        return new ResponseEntity<>(createdReportInventoryItem, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportInventoryItem> getReportInventoryItemById(@PathVariable("id") Long id) {
        ReportInventoryItem reportInventoryItem = reportInventoryItemRepository.findById(id)
                .orElse(null);
        if (reportInventoryItem != null) {
            return new ResponseEntity<>(reportInventoryItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportInventoryItem> updateReportInventoryItem(
            @PathVariable("id") Long id, @RequestBody ReportInventoryItem reportInventoryItem) {
        ReportInventoryItem existingReportInventoryItem = reportInventoryItemRepository.findById(id)
                .orElse(null);
        if (existingReportInventoryItem != null) {
            reportInventoryItem.setId(id);
            ReportInventoryItem updatedReportInventoryItem = reportInventoryItemRepository.save(reportInventoryItem);
            return new ResponseEntity<>(updatedReportInventoryItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReportInventoryItem(@PathVariable("id") Long id) {
        ReportInventoryItem reportInventoryItem = reportInventoryItemRepository.findById(id)
                .orElse(null);
        if (reportInventoryItem != null) {
            reportInventoryItemRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
