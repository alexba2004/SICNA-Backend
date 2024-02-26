package com.briones.sicnabackend.models;

import jakarta.persistence.*;

@Entity
@Table(name = "report_inventory_items")
public class ReportInventoryItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "report_id", referencedColumnName = "id")
    private Report report;
    
    @ManyToOne
    @JoinColumn(name = "inventory_item_id", referencedColumnName = "id")
    private InventoryItem inventoryItem;

    // Constructor, getters y setters
    public ReportInventoryItem() {
    }

    public ReportInventoryItem(Report report, InventoryItem inventoryItem) {
        this.report = report;
        this.inventoryItem = inventoryItem;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public InventoryItem getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(InventoryItem inventoryItem) {
        this.inventoryItem = inventoryItem;
    }
}
