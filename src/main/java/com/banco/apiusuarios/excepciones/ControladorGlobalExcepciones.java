package com.banco.apiusuarios.excepciones;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class ControladorGlobalExcepciones {

    @ExceptionHandler({InvalidEmailFormat.class, InvalidPasswordFormat.class})
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionesValidacion(RuntimeException ex) {
        String error = ex.getMessage();
        log.error("Excepción de validación: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionCorreoExistente(DuplicateEmailException ex) {
        String error = ex.getMessage();
        log.error("Excepción de correo ya registrado: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(error), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionViolacionRestricciones(ConstraintViolationException ex) {
        String error = ex.getMessage();
        String restriccionesVioladas = ex.getConstraintViolations().stream()
                .map(e-> e.getPropertyPath() + " " + e.getMessage())
                .collect(Collectors.joining(", "));
        log.error("Excepción de violación de restricciones: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(restriccionesVioladas), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ErrorResponseMessage> manejarExcepcionFormatoInvalido(InvalidFormatException ex) {
        String error = ex.getMessage();
        // El nombre propiedad que falló en serializar es siempre el último field del path
        String nombrePropiedad = ex.getPath().get(ex.getPath().size()-1).getFieldName();
        String valorPropiedad = ex.getValue().toString();
        String tipoEsperado = ex.getTargetType().getSimpleName();

        String mensajeRespuesta = String.format("La propiedad '%s': '%s' debe ser un %s", nombrePropiedad, valorPropiedad, tipoEsperado.toLowerCase());
        log.error("Excepción de serializacion de JSON: {}", error, ex);
        return new ResponseEntity<>(new ErrorResponseMessage(mensajeRespuesta), HttpStatus.BAD_REQUEST);
    }
}


