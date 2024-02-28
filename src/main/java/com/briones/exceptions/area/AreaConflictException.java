package com.briones.exceptions.area;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AreaConflictException extends RuntimeException {

    public AreaConflictException(String message) {
        super(message);
    }
}
