package com.unisal.api_venta_boletos_cine.exception;

public class ConflictoException extends RuntimeException {
    public ConflictoException(String mensaje) {
        super(mensaje);
    }
}