package com.briones.exceptions.studentuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class StudentUserConflictException extends RuntimeException {
    public StudentUserConflictException(String message) {
        super(message);
    }
}

