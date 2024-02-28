package com.briones.sicnabackend.exceptions.reportinventoryitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ReportInventoryItemNotFoundException extends RuntimeException {

    public ReportInventoryItemNotFoundException(String message) {
        super(message);
    }
}

