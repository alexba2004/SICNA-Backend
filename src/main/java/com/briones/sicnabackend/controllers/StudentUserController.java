package com.briones.sicnabackend.controllers;

import com.briones.exceptions.studentuser.*;
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
        return studentOptional.map(ResponseEntity::ok).orElseThrow(() ->
                new StudentUserNotFoundException("Student with ID " + studentId + " not found"));
    }

    @PostMapping
    public ResponseEntity<StudentUser> createAdminUser(@RequestBody StudentUser student) {
        if (student.getId() != null) {
            throw new StudentUserConflictException("Cannot create student with predefined ID");
        }
        StudentUser createdStudent = studentUserRepository.save(student);
        return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
    }
    

    @PutMapping("/{studentId}")
    public ResponseEntity<StudentUser> updateStudent(@PathVariable Long studentId, @RequestBody StudentUser updatedStudent) {
        Optional<StudentUser> studentOptional = studentUserRepository.findByStudentId(studentId);
        if (studentOptional.isPresent()) {
            if (!studentId.equals(updatedStudent.getId())) {
                throw new StudentUserBadRequestException("Student ID in path and body must match");
            }
            StudentUser savedStudent = studentUserRepository.save(updatedStudent);
            return ResponseEntity.ok(savedStudent);
        } else {
            throw new StudentUserNotFoundException("Student with ID " + studentId + " not found");
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long studentId) {
        Optional<StudentUser> studentUserOptional = studentUserRepository.findByStudentId(studentId);
        if (studentUserOptional.isPresent()) {
            studentUserRepository.delete(studentUserOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new StudentUserNotFoundException("Student with ID " + studentId + " not found");
        }
    }
}
