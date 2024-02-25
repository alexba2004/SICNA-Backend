package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_users")
public class AdminUser {
   
   @Id
   @Column(name = "employee_number", nullable = false, unique = true, length = 7)
   private String employeeNumber; // Número de trabajador

   @Column(nullable = false)
   private String name;

   @Column(name = "phone_number")
   private String phoneNumber;
   
   @Column(nullable = false, unique = true)
   private String email;
   
   @Column(nullable = false)
   private String password;

   @OneToMany(mappedBy = "adminUser")
   private List<Report> reports;

   // Getters y setters
}
