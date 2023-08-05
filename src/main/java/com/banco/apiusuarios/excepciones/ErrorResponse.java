package com.banco.apiusuarios.excepciones;

import lombok.Data;

@Data
public class ErrorResponse {
    private String mensaje;

    public ErrorResponse(String mensaje) {
        this.mensaje = mensaje;
    }
}