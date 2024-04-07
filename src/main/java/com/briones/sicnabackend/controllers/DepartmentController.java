package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.models.Department;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.repositories.AreaRepository;
import com.briones.sicnabackend.repositories.DepartmentRepository;
import com.briones.sicnabackend.repositories.PersonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private PersonRepository personRepository;

    // Obtener todos los departamentos
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return ResponseEntity.ok(departments);
    }

    // Obtener un departamento por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDepartmentById(@PathVariable Long id) {
        Optional<Department> departmentOptional = departmentRepository.findById(id);
        if (departmentOptional.isPresent()) {
            return ResponseEntity.ok(departmentOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Departamento no encontrado"));
        }
    }

    // Crear un nuevo departamento
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department) {
        // Verificar si el área asociada al departamento ya está administrada
        Area area = department.getArea();
        if (area != null && area.getId() != null) {
            area = areaRepository.findById(area.getId()).orElse(null);
        }
        // Establecer el área en el departamento
        department.setArea(area);

        // Verificar si la persona responsable asociada al departamento ya está administrada
        Person responsiblePerson = department.getResponsiblePerson();
        if (responsiblePerson != null && responsiblePerson.getId() != null) {
            responsiblePerson = personRepository.findById(responsiblePerson.getId()).orElse(null);
        }
        // Establecer la persona responsable en el departamento
        department.setResponsiblePerson(responsiblePerson);

        // Establecer fechas de registro y modificación
        Date currentDate = new Date();
        department.setRegistrationDate(currentDate);
        department.setModificationDate(currentDate);

        // Guardar el departamento
        Department createdDepartment = departmentRepository.save(department);
        return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
    }

    // Actualizar un departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody Department updatedDepartment) {
        try {
            Optional<Department> departmentOptional = departmentRepository.findById(id);
            if (departmentOptional.isPresent()) {
                Department existingDepartment = departmentOptional.get();
                existingDepartment.setName(updatedDepartment.getName());
                existingDepartment.setDescription(updatedDepartment.getDescription());
                existingDepartment.setArea(updatedDepartment.getArea());
                existingDepartment.setResponsiblePerson(updatedDepartment.getResponsiblePerson());
                Department savedDepartment = departmentRepository.save(existingDepartment);
                return ResponseEntity.ok(savedDepartment);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Departamento no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el departamento");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar un departamento por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id) {
        try {
            if (departmentRepository.existsById(id)) {
                departmentRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Departamento eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Departamento no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el departamento");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
