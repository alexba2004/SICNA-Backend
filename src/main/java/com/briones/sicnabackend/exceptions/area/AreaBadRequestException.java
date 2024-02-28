package com.briones.sicnabackend.exceptions.area;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AreaBadRequestException extends RuntimeException {

    public AreaBadRequestException(String message) {
        super(message);
    }
}

