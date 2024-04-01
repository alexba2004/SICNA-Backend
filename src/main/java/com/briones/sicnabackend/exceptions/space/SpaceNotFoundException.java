package com.briones.sicnabackend.exceptions.space;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SpaceNotFoundException extends RuntimeException {

    public SpaceNotFoundException(String message) {
        super(message);
    }
}

