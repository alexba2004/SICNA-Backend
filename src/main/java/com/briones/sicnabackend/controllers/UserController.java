package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.ErrorResponse;
import com.briones.sicnabackend.models.Area;
import com.briones.sicnabackend.models.Career;
import com.briones.sicnabackend.models.Person;
import com.briones.sicnabackend.models.User;
import com.briones.sicnabackend.repositories.AreaRepository;
import com.briones.sicnabackend.repositories.CareerRepository;
import com.briones.sicnabackend.repositories.PersonRepository;
import com.briones.sicnabackend.repositories.UserRepository;
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
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private CareerRepository careerRepository;

    @Autowired
    private PersonRepository personRepository;

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado"));
        }
    }

    // Crear un nuevo usuario
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        // Verificar si la persona asociada al usuario ya está administrada
        Person person = user.getPerson();
        if (person != null && person.getId() != null) {
            person = personRepository.findById(person.getId()).orElse(null);
        }
        // Establecer la persona en el usuario
        user.setPerson(person);

        // Establecer fechas de registro y modificación
        Date currentDate = new Date();
        user.setRegistrationDate(currentDate);
        user.setModificationDate(currentDate);

        // Guardar el usuario
        User createdUser = userRepository.save(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        try {
            Optional<User> userOptional = userRepository.findById(id);
            if (userOptional.isPresent()) {
                User existingUser = userOptional.get();
                existingUser.setUserType(updatedUser.getUserType());
                existingUser.setPassword(updatedUser.getPassword());
                existingUser.setPerson(updatedUser.getPerson());
                existingUser.setStatus(updatedUser.getStatus());
                User savedUser = userRepository.save(existingUser);
                return ResponseEntity.ok(savedUser);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al actualizar el usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // Eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                Map<String, Object> response = new HashMap<>();
                response.put("status", HttpStatus.OK.value());
                response.put("message", "Usuario eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Usuario no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error al eliminar el usuario");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
