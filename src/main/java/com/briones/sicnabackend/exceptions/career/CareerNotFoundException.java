package com.briones.sicnabackend.exceptions.career;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CareerNotFoundException extends RuntimeException {

    public CareerNotFoundException(String message) {
        super(message);
    }
}

