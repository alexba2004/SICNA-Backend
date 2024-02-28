package com.briones.exceptions.area;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AreaNotFoundException extends RuntimeException {

    public AreaNotFoundException(String message) {
        super(message);
    }
}

