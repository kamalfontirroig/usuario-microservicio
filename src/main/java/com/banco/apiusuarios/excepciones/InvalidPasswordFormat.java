package com.banco.apiusuarios.excepciones;

public class InvalidPasswordFormat extends RuntimeException {
    public InvalidPasswordFormat(String message) {
        super(message);
    }
}
