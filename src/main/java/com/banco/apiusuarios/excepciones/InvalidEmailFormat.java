package com.banco.apiusuarios.excepciones;

public class InvalidEmailFormat extends RuntimeException {
    public InvalidEmailFormat(String message) {
        super(message);
    }
}
