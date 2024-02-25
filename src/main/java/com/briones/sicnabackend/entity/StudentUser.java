package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "student_users")
public class StudentUser {
   
   @Id
   @Column(name = "student_id", nullable = false, unique = true)
   private String studentId; // Matrícula

   @Column(nullable = false)
   private String name;
   
   @Column(name = "phone_number")
   private String phoneNumber;
   
   @Column(nullable = false, unique = true)
   private String email;
   
   @Column(nullable = false)
   private String password;

   @OneToMany(mappedBy = "studentUser")
   private List<Report> reports;

   // Getters y setters
}
