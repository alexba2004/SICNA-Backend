package com.briones.sicnabackend.models;

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
   public String getEmployeeNumber() {
      return employeeNumber;
   }

   public void setEmployeeNumber(String employeeNumber) {
      this.employeeNumber = employeeNumber;
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
