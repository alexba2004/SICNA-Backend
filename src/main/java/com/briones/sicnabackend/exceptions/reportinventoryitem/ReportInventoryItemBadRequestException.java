package com.briones.sicnabackend.exceptions.reportinventoryitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReportInventoryItemBadRequestException extends RuntimeException {

    public ReportInventoryItemBadRequestException(String message) {
        super(message);
    }
}
