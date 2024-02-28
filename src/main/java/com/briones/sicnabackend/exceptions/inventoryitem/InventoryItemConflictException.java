package com.briones.sicnabackend.exceptions.inventoryitem;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InventoryItemConflictException extends RuntimeException {

    public InventoryItemConflictException(String message) {
        super(message);
    }
}

