package com.briones.exceptions.adminuser;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AdminUserConflictException extends RuntimeException {
    public AdminUserConflictException(String message) {
        super(message);
    }
}
