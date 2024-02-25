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
}
