package com.briones.sicnabackend.models;

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
    private StudentUser student;

    @ManyToOne
    @JoinColumn(name = "admin_user_id", nullable = false)
    private AdminUser adminUser;

    @OneToMany(mappedBy = "report", cascade = CascadeType.ALL)
    private List<ReportInventoryItem> reportInventoryItems;

    // Constructor, getters y setters
    public Report() {
    }

    public Report(LocalDate creationDate, String description, String status, StudentUser student, AdminUser adminUser) {
        this.creationDate = creationDate;
        this.description = description;
        this.status = status;
        this.student = student;
        this.adminUser = adminUser;
    }

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

    public StudentUser getStudent() {
        return student;
    }

    public void setStudent(StudentUser student) {
        this.student = student;
    }

    public AdminUser getAdminUser() {
        return adminUser;
    }

    public void setAdminUser(AdminUser adminUser) {
        this.adminUser = adminUser;
    }

    public List<ReportInventoryItem> getReportInventoryItems() {
        return reportInventoryItems;
    }

    public void setReportInventoryItems(List<ReportInventoryItem> reportInventoryItems) {
        this.reportInventoryItems = reportInventoryItems;
    }
}
