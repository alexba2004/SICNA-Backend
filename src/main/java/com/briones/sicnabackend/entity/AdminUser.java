package com.briones.sicnabackend.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "admin_users")
public class AdminUser {
    
    @Id
    @Column(name = "employee_number", nullable = false, unique = true)
    private String employeeNumber; // Número de trabajador

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "adminUser")
    private List<Report> reports;

    // Getters y setters
}
