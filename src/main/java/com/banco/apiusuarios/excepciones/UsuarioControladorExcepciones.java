package com.banco.apiusuarios.excepciones;

import com.banco.apiusuarios.servicio.UsuarioServicioImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuarioControladorExcepciones {

    private static final Logger log = LoggerFactory.getLogger(UsuarioControladorExcepciones.class);

    @ExceptionHandler({InvalidEmailFormat.class,InvalidPasswordFormat.class})
    public ResponseEntity<ErrorResponse> manejarExcepcionesDeValidacion(RuntimeException ex) {
        String error = ex.getMessage();
        log.error("Excepción de validación: ",ex);
        return new ResponseEntity<>(new ErrorResponse(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponse> manejarExcepcionDeCorreoExistente(DuplicateEmailException ex) {
        log.error("Excepción de correo ya registrado: ",ex);
        return new ResponseEntity<>(new ErrorResponse(ex.getMessage()), HttpStatus.CONFLICT);
    }

}