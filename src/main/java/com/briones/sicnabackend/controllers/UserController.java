package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.exceptions.user.UserNotFoundException;
import com.briones.sicnabackend.models.User;
import com.briones.sicnabackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(ResponseEntity::ok)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // Create a new user
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    // Update an existing user
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(existingUser -> {
            existingUser.setUserType(userDetails.getUserType());
            existingUser.setPassword(userDetails.getPassword());
            existingUser.setPerson(userDetails.getPerson());
            existingUser.setStatus(userDetails.getStatus());
            User updatedUser = userRepository.save(existingUser);
            return ResponseEntity.ok(updatedUser);
        }).orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    // Delete a user
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }
}

