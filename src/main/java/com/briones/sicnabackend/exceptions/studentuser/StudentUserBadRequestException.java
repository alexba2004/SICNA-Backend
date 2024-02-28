package com.briones.sicnabackend.exceptions.studentuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class StudentUserBadRequestException extends RuntimeException {
    public StudentUserBadRequestException(String message) {
        super(message);
    }
}

