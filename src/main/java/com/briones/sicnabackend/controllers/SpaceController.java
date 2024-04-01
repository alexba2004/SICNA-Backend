package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.space.SpaceNotFoundException;
import com.briones.sicnabackend.models.Space;
import com.briones.sicnabackend.repositories.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/spaces")
public class SpaceController {

    @Autowired
    private SpaceRepository spaceRepository;

    // Get all spaces
    @GetMapping
    public ResponseEntity<List<Space>> getAllSpaces() {
        List<Space> spaces = spaceRepository.findAll();
        return ResponseEntity.ok(spaces);
    }

    // Get space by id
    @GetMapping("/{id}")
    public ResponseEntity<Space> getSpaceById(@PathVariable Long id) {
        Optional<Space> spaceOptional = spaceRepository.findById(id);
        return spaceOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new SpaceNotFoundException("Space not found with id: " + id));
    }

    // Create a new space
    @PostMapping
    public ResponseEntity<Space> createSpace(@RequestBody Space space) {
        Space createdSpace = spaceRepository.save(space);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdSpace);
    }

    // Update an existing space
    @PutMapping("/{id}")
    public ResponseEntity<Space> updateSpace(@PathVariable Long id, @RequestBody Space spaceDetails) {
        Optional<Space> spaceOptional = spaceRepository.findById(id);
        return spaceOptional.map(existingSpace -> {
            existingSpace.setName(spaceDetails.getName());
            existingSpace.setDescription(spaceDetails.getDescription());
            existingSpace.setDepartment(spaceDetails.getDepartment());
            existingSpace.setResponsiblePerson(spaceDetails.getResponsiblePerson());
            Space updatedSpace = spaceRepository.save(existingSpace);
            return ResponseEntity.ok(updatedSpace);
        }).orElseThrow(() -> new SpaceNotFoundException("Space not found with id: " + id));
    }

    // Delete a space
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable Long id) {
        if (spaceRepository.existsById(id)) {
            spaceRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new SpaceNotFoundException("Space not found with id: " + id);
        }
    }
}

