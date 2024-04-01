package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.person.PersonNotFoundException;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    // Get all persons
    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok(persons);
    }

    // Get person by id
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> personOptional = personRepository.findById(id);
        return personOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    // Create a new person
    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person createdPerson = personRepository.save(person);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPerson);
    }

    // Update an existing person
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Optional<Person> personOptional = personRepository.findById(id);
        return personOptional.map(existingPerson -> {
            existingPerson.setFirstName(personDetails.getFirstName());
            existingPerson.setLastName(personDetails.getLastName());
            existingPerson.setMiddleName(personDetails.getMiddleName());
            existingPerson.setBirthDate(personDetails.getBirthDate());
            existingPerson.setEmail(personDetails.getEmail());
            existingPerson.setPhone(personDetails.getPhone());
            existingPerson.setRegistrationNumber(personDetails.getRegistrationNumber());
            existingPerson.setPersonType(personDetails.getPersonType());
            existingPerson.setArea(personDetails.getArea());
            existingPerson.setCareer(personDetails.getCareer());
            Person updatedPerson = personRepository.save(existingPerson);
            return ResponseEntity.ok(updatedPerson);
        }).orElseThrow(() -> new PersonNotFoundException("Person not found with id: " + id));
    }

    // Delete a person
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        if (personRepository.existsById(id)) {
            personRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new PersonNotFoundException("Person not found with id: " + id);
        }
    }
}

