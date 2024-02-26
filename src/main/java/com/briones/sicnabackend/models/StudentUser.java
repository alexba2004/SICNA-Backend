package com.briones.sicnabackend.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "student_users")
public class StudentUser {
   
   @Id
   @Column(name = "student_id", nullable = false, unique = true, length = 6)
   private String studentId; // Matrícula

   @Column(nullable = false)
   private String name;
   
   @Column(name = "phone_number")
   private String phoneNumber;
   
   @Column(nullable = false, unique = true)
   private String email;
   
   @Column(nullable = false)
   private String password;

   @OneToMany(mappedBy = "student")
   private List<Report> reports;   

   // Getters y setters
   public String getStudentId() {
      return studentId;
   }

   public void setStudentId(String studentId) {
      this.studentId = studentId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhoneNumber() {
      return phoneNumber;
   }

   public void setPhoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public List<Report> getReports() {
      return reports;
   }

   public void setReports(List<Report> reports) {
      this.reports = reports;
   }
}