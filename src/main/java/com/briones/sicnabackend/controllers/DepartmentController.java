package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.department.DepartmentNotFoundException;
import com.briones.sicnabackend.models.Department;
import com.briones.sicnabackend.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Get all departments
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(departments);
    }

    // Get department by id
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
    }

    // Create a new department
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        Department createdDepartment = departmentRepository.save(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    // Update an existing department
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        return departmentOptional.map(existingDepartment -> {
            existingDepartment.setName(departmentDetails.getName());
            existingDepartment.setDescription(departmentDetails.getDescription());
            existingDepartment.setArea(departmentDetails.getArea());
            existingDepartment.setResponsiblePerson(departmentDetails.getResponsiblePerson());
            Department updatedDepartment = departmentRepository.save(existingDepartment);
            return ResponseEntity.ok(updatedDepartment);
        }).orElseThrow(() -> new DepartmentNotFoundException("Department not found with id: " + id));
    }

    // Delete a department
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new DepartmentNotFoundException("Department not found with id: " + id);
        }
    }
}