package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "inventory_items")
public class InventoryItem {
   
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
   
   @Column(nullable = false)
   private String name;
   
   @Column(nullable = false)
   private String brand;
   
   @Column(nullable = false)
   private String category;
   
   @Column(nullable = false)
   private double price;
   
   @Column(columnDefinition = "TEXT")
   private String description;

   @Column(name = "image_file_name")
   private String imageFileName;

   @Column(name = "created_at", nullable = false)
   private Date createdAt;

   @ManyToOne
   @JoinColumn(name = "area_id", nullable = false)
   private Area area;

   // Getters y setters
}
