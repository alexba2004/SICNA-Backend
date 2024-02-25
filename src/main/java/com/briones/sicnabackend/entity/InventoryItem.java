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
   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getBrand() {
      return brand;
   }

   public void setBrand(String brand) {
      this.brand = brand;
   }

   public String getCategory() {
      return category;
   }

   public void setCategory(String category) {
      this.category = category;
   }

   public double getPrice() {
      return price;
   }

   public void setPrice(double price) {
      this.price = price;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
   }

   public String getImageFileName() {
      return imageFileName;
   }

   public void setImageFileName(String imageFileName) {
      this.imageFileName = imageFileName;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public Area getArea() {
      return area;
   }

   public void setArea(Area area) {
      this.area = area;
   }
}
