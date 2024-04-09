package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.models.Career;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.repositories.AreaRepository;
import com.briones.sicnabackend.repositories.CareerRepository;
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
@RequestMapping("/sicna/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CareerRepository careerRepository;

    // Obtener todas las personas
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }

    // Obtener una persona por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        if (personOptional.isPresent()) {
            return ResponseEntity.ok(personOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Persona no encontrada"));
        }
    }

    // Crear una nueva persona
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        // Verificar si el área asociada a la persona ya está administrada
        Area area = person.getArea();
        if (area != null && area.getId() != null) {
            area = areaRepository.findById(area.getId()).orElse(null);
        }
        // Establecer el área en la persona
        person.setArea(area);

        // Verificar si la carrera asociada a la persona ya está administrada
        Career career = person.getCareer();
        if (career != null && career.getId() != null) {
            career = careerRepository.findById(career.getId()).orElse(null);
        }
        // Establecer la carrera en la persona
        person.setCareer(career);

        // Establecer fechas de registro y modificación
        Date currentDate = new Date();
        person.setRegistrationDate(currentDate);
        person.setModificationDate(currentDate);

        // Guardar la persona
        Person createdPerson = personRepository.save(person);
        return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
    }

    // Actualizar una persona existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePerson(@PathVariable Long id, @RequestBody Person updatedPerson) {
        try {
            Optional<Person> personOptional = personRepository.findById(id);
            if (personOptional.isPresent()) {
                Person existingPerson = personOptional.get();
                existingPerson.setFirstName(updatedPerson.getFirstName());
                existingPerson.setLastName(updatedPerson.getLastName());
                existingPerson.setMiddleName(updatedPerson.getMiddleName());
                existingPerson.setBirthDate(updatedPerson.getBirthDate());
                existingPerson.setEmail(updatedPerson.getEmail());
                existingPerson.setPhone(updatedPerson.getPhone());
                existingPerson.setRegistrationNumber(updatedPerson.getRegistrationNumber());
                existingPerson.setPersonType(updatedPerson.getPersonType());
                existingPerson.setArea(updatedPerson.getArea());
                existingPerson.setCareer(updatedPerson.getCareer());
                Person savedPerson = personRepository.save(existingPerson);
                return ResponseEntity.ok(savedPerson);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar la persona");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar una persona por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePerson(@PathVariable Long id) {
        try {
            if (personRepository.existsById(id)) {
                personRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Persona eliminada exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Persona no encontrada");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar la persona");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
