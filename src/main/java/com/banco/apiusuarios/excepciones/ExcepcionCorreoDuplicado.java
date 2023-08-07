package com.banco.apiusuarios.excepciones;

public class ExcepcionCorreoDuplicado extends RuntimeException {
    public ExcepcionCorreoDuplicado(String message){
        super(message);
    }
}
