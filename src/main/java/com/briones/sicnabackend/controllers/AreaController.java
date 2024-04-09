package com.briones.sicnabackend.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briones.sicnabackend.exceptions.*;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.repositories.AreaRepository;

@RestController
@RequestMapping("/sicna/areas")
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    // Obtener todas las áreas
    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return ResponseEntity.ok(areas);
    }

    // Obtener un área por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getAreaById(@PathVariable Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        if (areaOptional.isPresent()) {
            return ResponseEntity.ok(areaOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Área no encontrada"));
        }
    }

    // Crear un área nueva
    @PostMapping
    public ResponseEntity<?> createArea(@RequestBody Area area) {
        try {
            Area savedArea = areaRepository.save(area);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedArea);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al crear el área");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Actualizar un área existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateArea(@PathVariable Long id, @RequestBody Area updatedArea) {
        try {
            Optional<Area> areaOptional = areaRepository.findById(id);
            if (areaOptional.isPresent()) {
                Area existingArea = areaOptional.get();
                if (updatedArea.getRegistrationDate() == null) {
                    updatedArea.setRegistrationDate(existingArea.getRegistrationDate());
                }
                updatedArea.setId(id);
                Area savedArea = areaRepository.save(updatedArea);
                return ResponseEntity.ok(savedArea);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Área no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el área");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar un área por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteArea(@PathVariable Long id) {
        try {
            if (areaRepository.existsById(id)) {
                areaRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Área eliminada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Área no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el área");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}

