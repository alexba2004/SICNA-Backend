package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.area.AreaNotFoundException;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.repositories.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/areas")
public class AreaController {

    @Autowired
    private AreaRepository areaRepository;

    // Get all areas
    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return ResponseEntity.ok(areas);
    }

    // Get area by id
    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        return areaOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new AreaNotFoundException("Area not found with id: " + id));
    }

    // Create a new area
    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        Area createdArea = areaRepository.save(area);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArea);
    }

    // Update an existing area
    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(@PathVariable Long id, @RequestBody Area areaDetails) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        return areaOptional.map(existingArea -> {
            existingArea.setName(areaDetails.getName());
            existingArea.setDescription(areaDetails.getDescription());
            Area updatedArea = areaRepository.save(existingArea);
            return ResponseEntity.ok(updatedArea);
        }).orElseThrow(() -> new AreaNotFoundException("Area not found with id: " + id));
    }

    // Delete an area
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        if (areaRepository.existsById(id)) {
            areaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new AreaNotFoundException("Area not found with id: " + id);
        }
    }
}

