package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.career.CareerNotFoundException;
import com.briones.sicnabackend.models.Career;
import com.briones.sicnabackend.repositories.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/careers")
public class CareerController {

    @Autowired
    private CareerRepository careerRepository;

    // Get all careers
    @GetMapping
    public ResponseEntity<List<Career>> getAllCareers() {
        List<Career> careers = careerRepository.findAll();
        return ResponseEntity.ok(careers);
    }

    // Get career by id
    @GetMapping("/{id}")
    public ResponseEntity<Career> getCareerById(@PathVariable Long id) {
        Optional<Career> careerOptional = careerRepository.findById(id);
        return careerOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new CareerNotFoundException("Career not found with id: " + id));
    }

    // Create a new career
    @PostMapping
    public ResponseEntity<Career> createCareer(@RequestBody Career career) {
        Career createdCareer = careerRepository.save(career);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCareer);
    }

    // Update an existing career
    @PutMapping("/{id}")
    public ResponseEntity<Career> updateCareer(@PathVariable Long id, @RequestBody Career careerDetails) {
        Optional<Career> careerOptional = careerRepository.findById(id);
        return careerOptional.map(existingCareer -> {
            existingCareer.setName(careerDetails.getName());
            existingCareer.setDescription(careerDetails.getDescription());
            existingCareer.setArea(careerDetails.getArea());
            Career updatedCareer = careerRepository.save(existingCareer);
            return ResponseEntity.ok(updatedCareer);
        }).orElseThrow(() -> new CareerNotFoundException("Career not found with id: " + id));
    }

    // Delete a career
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCareer(@PathVariable Long id) {
        if (careerRepository.existsById(id)) {
            careerRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new CareerNotFoundException("Career not found with id: " + id);
        }
    }
}
