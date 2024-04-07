package com.briones.sicnabackend.exceptions;

public class ErrorResponse {
    private int status;
    private String message;

    // Constructor sin argumentos
    public ErrorResponse() {
    }

    // Constructor con argumentos
    public ErrorResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters y setters
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
