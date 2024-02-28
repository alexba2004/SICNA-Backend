package com.briones.exceptions.report;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReportConflictException extends RuntimeException {

    public ReportConflictException(String message) {
        super(message);
    }
}

