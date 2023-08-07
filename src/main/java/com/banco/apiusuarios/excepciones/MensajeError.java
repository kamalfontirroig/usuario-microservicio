package com.banco.apiusuarios.excepciones;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MensajeError {
    private final String mensaje;

    public MensajeError(String mensaje) {
        this.mensaje = mensaje;
    }
}
