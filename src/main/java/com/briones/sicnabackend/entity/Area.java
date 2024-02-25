package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "areas")
public class Area {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "area")
    private List<InventoryItem> inventoryItems;

    // Getters y setters
}
