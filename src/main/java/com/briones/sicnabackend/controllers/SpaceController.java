package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Department;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.models.Space;
import com.briones.sicnabackend.repositories.DepartmentRepository;
import com.briones.sicnabackend.repositories.PersonRepository;
import com.briones.sicnabackend.repositories.SpaceRepository;
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
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceRepository spaceRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private PersonRepository personRepository; 

    // Obtener todos los espacios
    @GetMapping
    public ResponseEntity<List<Space>> getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();
        return ResponseEntity.ok(spaces);
    }

    // Obtener un espacio por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getSpaceById(@PathVariable Long id) {
        Optional<Space> spaceOptional = spaceRepository.findById(id);
        if (spaceOptional.isPresent()) {
            return ResponseEntity.ok(spaceOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Espacio no encontrado"));
        }
    }

    // Crear un nuevo espacio
    @PostMapping
    public ResponseEntity<Space> createSpace(@RequestBody Space space) {
        // Verificar si el departamento asociado al espacio ya está administrado
        Department department = space.getDepartment();
        if (department != null && department.getId() != null) {
            department = departmentRepository.findById(department.getId()).orElse(null);
        }
        // Establecer el departamento en el espacio
        space.setDepartment(department);

        // Verificar si la persona responsable asociada al espacio ya está administrada
        Person responsiblePerson = space.getResponsiblePerson();
        if (responsiblePerson != null && responsiblePerson.getId() != null) {
            responsiblePerson = personRepository.findById(responsiblePerson.getId()).orElse(null);
        }
        // Establecer la persona responsable en el espacio
        space.setResponsiblePerson(responsiblePerson);

        // Establecer fechas de registro y modificación
        Date currentDate = new Date();
        space.setRegistrationDate(currentDate);
        space.setModificationDate(currentDate);

        // Guardar el espacio
        Space createdSpace = spaceRepository.save(space);
        return new ResponseEntity<>(createdSpace, HttpStatus.CREATED);
    }

    // Actualizar un espacio existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSpace(@PathVariable Long id, @RequestBody Space updatedSpace) {
        try {
            Optional<Space> spaceOptional = spaceRepository.findById(id);
            if (spaceOptional.isPresent()) {
                Space existingSpace = spaceOptional.get();
                existingSpace.setName(updatedSpace.getName());
                existingSpace.setDescription(updatedSpace.getDescription());
                existingSpace.setDepartment(updatedSpace.getDepartment());
                existingSpace.setResponsiblePerson(updatedSpace.getResponsiblePerson());
                Space savedSpace = spaceRepository.save(existingSpace);
                return ResponseEntity.ok(savedSpace);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Espacio no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el espacio");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar un espacio por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSpace(@PathVariable Long id) {
        try {
            if (spaceRepository.existsById(id)) {
                spaceRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Espacio eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Espacio no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el espacio");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
