package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Space;
import com.briones.sicnabackend.repositories.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceRepository spaceRepository;

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
    public ResponseEntity<?> createSpace(@RequestBody Space space) {
        try {
            Space createdSpace = spaceRepository.save(space);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSpace);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el espacio");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
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
