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

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.models.Career;
import com.briones.sicnabackend.repositories.AreaRepository;
import com.briones.sicnabackend.repositories.CareerRepository;

@RestController
@RequestMapping("/api/careers")
public class CareerController {

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private AreaRepository areaRepository;

    // Obtener todas las carreras
    @GetMapping
    public ResponseEntity<List<Career>> getAllCareers() {
        List<Career> careers = careerRepository.findAll();
        return ResponseEntity.ok(careers);
    }

    // Obtener una carrera por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getCareerById(@PathVariable Long id) {
        Optional<Career> careerOptional = careerRepository.findById(id);
        if (careerOptional.isPresent()) {
            return ResponseEntity.ok(careerOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Carrera no encontrada"));
        }
    }

    // Crear una carrera nueva
    @PostMapping
    public ResponseEntity<Career> createCareer(@RequestBody Career career) {
        // Obtener el área asociada a la carrera
        Area area = career.getArea();
        if (area != null && area.getId() != null) {
            // Si el área ya tiene un ID, buscarla en la base de datos para asegurarse de que esté administrada
            area = areaRepository.findById(area.getId()).orElse(null);
        }
        // Establecer el área en la carrera (puede ser la instancia recuperada o la instancia original)
        career.setArea(area);
        // Guardar la carrera
        Career createdCareer = careerRepository.save(career);
        return new ResponseEntity<>(createdCareer, HttpStatus.CREATED);
    }
    
    // Actualizar una carrera existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateCareer(@PathVariable Long id, @RequestBody Career updatedCareer) {
        try {
            Optional<Career> careerOptional = careerRepository.findById(id);
            if (careerOptional.isPresent()) {
                Career existingCareer = careerOptional.get();
                existingCareer.setName(updatedCareer.getName());
                existingCareer.setDescription(updatedCareer.getDescription());
                Career savedCareer = careerRepository.save(existingCareer);
                return ResponseEntity.ok(savedCareer);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Carrera no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la carrera");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar una carrera por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCareer(@PathVariable Long id) {
        try {
            if (careerRepository.existsById(id)) {
                careerRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Carrera eliminada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Carrera no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar la carrera");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
