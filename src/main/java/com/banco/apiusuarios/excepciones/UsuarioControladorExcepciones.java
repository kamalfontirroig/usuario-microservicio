package com.banco.apiusuarios.excepciones;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class UsuarioControladorExcepciones {

    @ExceptionHandler({InvalidEmailFormat.class, InvalidPasswordFormat.class})
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionesDeValidacion(RuntimeException ex) {
        String error = ex.getMessage();
        log.error("Excepción de validación: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionDeCorreoExistente(DuplicateEmailException ex) {
        String error = ex.getMessage();
        log.error("Excepción de correo ya registrado: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(error), HttpStatus.CONFLICT);
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseMessage> manejarConstraintViolationException(ConstraintViolationException ex) {
        String error = ex.getMessage();
        String constraints = ex.getConstraintViolations().stream()
                .map(e-> e.getPropertyPath() + " " + e.getMessage())
                .collect(Collectors.joining(", "));
        log.error("Excepción de violación de restricciones: {}", constraints, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(constraints), HttpStatus.BAD_REQUEST);
    }

}
