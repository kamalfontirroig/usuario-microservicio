package com.banco.apiusuarios.excepciones;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ErrorResponseMessage {
    private final String mensaje;

    public ErrorResponseMessage(String mensaje) {
        this.mensaje = mensaje;
    }
}
