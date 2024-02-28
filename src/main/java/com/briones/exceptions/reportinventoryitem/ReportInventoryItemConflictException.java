package com.briones.exceptions.reportinventoryitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ReportInventoryItemConflictException extends RuntimeException {

    public ReportInventoryItemConflictException(String message) {
        super(message);
    }
}

