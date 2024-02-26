package com.briones.sicnabackend.lib.sedeers;

import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import com.briones.sicnabackend.models.AdminUser;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.models.InventoryItem;
import com.briones.sicnabackend.models.Report;
import com.briones.sicnabackend.models.StudentUser;
import com.briones.sicnabackend.repositories.AdminUserRepository;
import com.briones.sicnabackend.repositories.AreaRepository;
import com.briones.sicnabackend.repositories.InventoryItemRepository;
import com.briones.sicnabackend.repositories.ReportRepository;
import com.briones.sicnabackend.repositories.StudentUserRepository;
import com.briones.sicnabackend.models.ReportInventoryItem;
import com.briones.sicnabackend.repositories.ReportInventoryItemRepository;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private StudentUserRepository studentUserRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private InventoryItemRepository inventoryItemRepository;

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private ReportInventoryItemRepository reportInventoryItemRepository;

    @Override
    public void run(String... args) {
        try {
            seedStudentUsers();
            seedAdminUsers();
            seedAreas();
            seedInventoryItems();
            seedReports();
            seedReportsInventoryItems();
        } catch (DataAccessException e) {
            System.out.println("Error al acceder a la base de datos: " + e.getMessage());
        }
    }

    private void seedStudentUsers() {
        if (studentUserRepository.count() == 0) {
            saveStudentUser("200000", "Jose Alejandro Briones Arroyo", "+1122334455", "alejandro@example.com", "estudiante123");
            saveStudentUser("200003", "Luis Octavio Lopez Martinez", "+9988776655", "luis@example.com", "estudiante012");
            saveStudentUser("200001", "Carlos Sánchez", "+9988776655", "carlos@example.com", "estudiante456");
            saveStudentUser("200002", "Elena Gómez", "+1122334455", "elena@example.com", "estudiante789");
        }
    }

    private void seedAdminUsers() {
        if (adminUserRepository.count() == 0) {
            saveAdminUser("2000002", "Joan Salas Vera", "+1122334455", "joan@example.com", "contraseña789");
            saveAdminUser("2000000", "Juan Pérez", "+1234567890", "juan@example.com", "contraseña123");
            saveAdminUser("2000001", "María García", "+1987654321", "maria@example.com", "contraseña456");
            saveAdminUser("2000003", "Ana Martínez", "+9988776655", "ana@example.com", "contraseña012");
        }
    }

    private void seedAreas() {
        if (areaRepository.count() == 0) {
            saveArea("Laboratorio de Cómputo 1", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 2", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 3", "Laboratorio de cómputo equipado con computadoras y software especializado");
            saveArea("Laboratorio de Cómputo 4", "Laboratorio de cómputo equipado con computadoras y software especializado");
        }
    }

    private void seedInventoryItems() {
        if (inventoryItemRepository.count() == 0) {
            saveInventoryItem("Monitor", "Samsung", "Electrónica", 300.00, "Monitor de 24 pulgadas", "monitor.jpg", new Date(), 1L);
            saveInventoryItem("Teclado", "Logitech", "Periférico", 50.00, "Teclado inalámbrico", "keyboard.jpg", new Date(), 2L);
            saveInventoryItem("Ratón", "Razer", "Periférico", 30.00, "Ratón óptico", "mouse.jpg", new Date(), 3L);
            saveInventoryItem("CPU", "Xtrem", "Equipo de Cómputo", 800.00, "Computadora de escritorio", "cpu.jpg", new Date(), 4L);
        }
    }

    private void seedReports() {
        if (reportRepository.count() == 0) {
            saveReport(new Date(), "Monitor roto", "ADEUDO", "200000", "2000002");
            saveReport(new Date(), "Teclado sin 4 teclas", "PAGADO", "200003", "2000000");
            saveReport(new Date(), "Ratón desgastado", "ADEUDO", "200001", "2000001");
            saveReport(new Date(), "CPU con problemas de arranque", "PAGADO", "200002", "2000003");
        }
    }

    private void saveStudentUser(String studentId, String name, String phoneNumber, String email, String password) {
        StudentUser studentUser = new StudentUser();
        studentUser.setStudentId(studentId);
        studentUser.setName(name);
        studentUser.setPhoneNumber(phoneNumber);
        studentUser.setEmail(email);
        studentUser.setPassword(password);
        studentUserRepository.save(studentUser);
    }

    private void saveAdminUser(String employeeNumber, String name, String phoneNumber, String email, String password) {
        AdminUser adminUser = new AdminUser();
        adminUser.setEmployeeNumber(employeeNumber);
        adminUser.setName(name);
        adminUser.setPhoneNumber(phoneNumber);
        adminUser.setEmail(email);
        adminUser.setPassword(password);
        adminUserRepository.save(adminUser);
    }

    private void saveArea(String name, String description) {
        Area area = new Area();
        area.setName(name);
        area.setDescription(description);
        areaRepository.save(area);
    }

    private void saveInventoryItem(String name, String brand, String category, double price, String description, String imageFileName, Date createdAt, Long areaId) {
        InventoryItem inventoryItem = new InventoryItem();
        inventoryItem.setName(name);
        inventoryItem.setBrand(brand);
        inventoryItem.setCategory(category);
        inventoryItem.setPrice(price);
        inventoryItem.setDescription(description);
        inventoryItem.setImageFileName(imageFileName);
        inventoryItem.setCreatedAt(createdAt);
        inventoryItem.setArea(areaRepository.findById(areaId).orElse(null));
        inventoryItemRepository.save(inventoryItem);
    }

    private void saveReport(Date creationDate, String description, String status, String studentId, String adminUserId) {
        Report report = new Report();
        report.setCreationDate(creationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        report.setDescription(description);
        report.setStatus(status);
        report.setStudent(studentUserRepository.findByStudentId(studentId).orElse(null));
        report.setAdminUser(adminUserRepository.findByEmployeeNumber(adminUserId).orElse(null));
        reportRepository.save(report);
    }

    private void seedReportsInventoryItems() {
        if (reportInventoryItemRepository.count() == 0) {
            List<Report> reports = reportRepository.findAll();
            List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();
        
            int itemsPerReport = 1;
        
            int numReports = reports.size();
            int numItems = inventoryItems.size();
        
            int maxItems = numReports * itemsPerReport;
            int numItemsToAssociate = Math.min(numItems, maxItems);
        
            for (int i = 0; i < numItemsToAssociate; i++) {
                Report report = reports.get(i);
                InventoryItem item = inventoryItems.get(i);
                associateInventoryItemWithReport(item, report);
            }
        }
    }
    
    private void associateInventoryItemWithReport(InventoryItem inventoryItem, Report report) {
            ReportInventoryItem reportInventoryItem = new ReportInventoryItem();
            reportInventoryItem.setReport(report);
            reportInventoryItem.setInventoryItem(inventoryItem);
            reportInventoryItemRepository.save(reportInventoryItem);
        }
    }
