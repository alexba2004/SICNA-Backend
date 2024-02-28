package com.briones.sicnabackend.exceptions.studentuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StudentUserNotFoundException extends RuntimeException {
    public StudentUserNotFoundException(String message) {
        super(message);
    }
}

