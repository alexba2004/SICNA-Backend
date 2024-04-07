package com.briones.sicnabackend.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbb_reports")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date loanDate;

    @Column(nullable = false)
    private Date returnDateLimit;

    @Column(nullable = false)
    private String status;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "producto_prestado_id", nullable = false)
    private Product borrowedProduct;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestamista_id", nullable = false)
    private Person lender;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "prestatario_id", nullable = false)
    private Person borrower;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDateLimit() {
        return returnDateLimit;
    }

    public void setReturnDateLimit(Date returnDateLimit) {
        this.returnDateLimit = returnDateLimit;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getBorrowedProduct() {
        return borrowedProduct;
    }

    public void setBorrowedProduct(Product borrowedProduct) {
        this.borrowedProduct = borrowedProduct;
    }

    public Person getLender() {
        return lender;
    }

    public void setLender(Person lender) {
        this.lender = lender;
    }

    public Person getBorrower() {
        return borrower;
    }

    public void setBorrower(Person borrower) {
        this.borrower = borrower;
    }

    public Date getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Date modificationDate) {
        this.modificationDate = modificationDate;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modificationDate = new Date();
    }
}
