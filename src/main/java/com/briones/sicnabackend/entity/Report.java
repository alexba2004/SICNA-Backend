package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "reports")
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String status; // Puede ser "PENDING", "APPROVED", "REJECTED", etc.

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentUser studentUser;

    @ManyToMany
    @JoinTable(
        name = "report_inventory_items",
        joinColumns = @JoinColumn(name = "report_id"),
        inverseJoinColumns = @JoinColumn(name = "inventory_item_id")
    )
    private List<InventoryItem> inventoryItems;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StudentUser getStudentUser() {
        return studentUser;
    }

    public void setStudentUser(StudentUser studentUser) {
        this.studentUser = studentUser;
    }

    public List<InventoryItem> getInventoryItems() {
        return inventoryItems;
    }

    public void setInventoryItems(List<InventoryItem> inventoryItems) {
        this.inventoryItems = inventoryItems;
    }
}
