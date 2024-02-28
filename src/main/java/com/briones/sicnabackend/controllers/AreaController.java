package com.briones.sicnabackend.controllers;

import com.briones.exceptions.area.*;
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

    @GetMapping
    public ResponseEntity<List<Area>> getAllAreas() {
        List<Area> areas = areaRepository.findAll();
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Area> getAreaById(@PathVariable Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        return areaOptional.map(ResponseEntity::ok).orElseThrow(() -> new AreaNotFoundException("Area not found with ID: " + id));
    }

    @PostMapping
    public ResponseEntity<Area> createArea(@RequestBody Area area) {
        if (area.getId() != null) {
            throw new AreaBadRequestException("Area ID must be null for creation.");
        }
        Area createdArea = areaRepository.save(area);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdArea);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Area> updateArea(@PathVariable Long id, @RequestBody Area updatedArea) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        if (areaOptional.isPresent()) {
            if (!id.equals(updatedArea.getId())) {
                throw new AreaBadRequestException("Area ID in path must match ID in request body.");
            }
            Area savedArea = areaRepository.save(updatedArea);
            return ResponseEntity.ok(savedArea);
        } else {
            throw new AreaNotFoundException("Area not found with ID: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArea(@PathVariable Long id) {
        Optional<Area> areaOptional = areaRepository.findById(id);
        if (areaOptional.isPresent()) {
            areaRepository.delete(areaOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new AreaNotFoundException("Area not found with ID: " + id);
        }
    }
}
