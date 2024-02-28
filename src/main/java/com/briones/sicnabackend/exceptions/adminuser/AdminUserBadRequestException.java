package com.briones.sicnabackend.exceptions.adminuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AdminUserBadRequestException extends RuntimeException {
    public AdminUserBadRequestException(String message) {
        super(message);
    }
}

