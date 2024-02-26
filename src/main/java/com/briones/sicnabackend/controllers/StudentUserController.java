package com.briones.sicnabackend.controllers;

import com.briones.sicnabackend.models.AdminUser;
import com.briones.sicnabackend.models.StudentUser;
import com.briones.sicnabackend.repositories.StudentUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/student-users")
public class StudentUserController {

    @Autowired
    private StudentUserRepository studentUserRepository;

    @GetMapping
    public ResponseEntity<List<StudentUser>> getAllStudents() {
        List<StudentUser> students = studentUserRepository.findAll();
        return ResponseEntity.ok(students);
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<StudentUser> getStudentByStudentId(@PathVariable Long studentId) {
        Optional<StudentUser> studentOptional = studentUserRepository.findByStudentId(studentId);
        return studentOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public StudentUser createAdminUser(@RequestBody StudentUser studentId) {
        return studentUserRepository.save(studentId);
    }
    

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentUser> updateStudent(@PathVariable Long studentId, @RequestBody StudentUser updatedStudent) {
        Optional<StudentUser> studentOptional = studentUserRepository.findByStudentId(studentId);
        if (studentOptional.isPresent()) {
            updatedStudent.setId(studentOptional.get().getId());
            StudentUser savedStudent = studentUserRepository.save(updatedStudent);
            return ResponseEntity.ok(savedStudent);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        Optional<StudentUser> studentUserOptional = studentUserRepository.findByStudentId(studentId);
        if (studentUserOptional.isPresent()) {
            studentUserRepository.delete(studentUserOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
