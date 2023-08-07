package com.banco.apiusuarios.excepciones;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@Log4j2
@ControllerAdvice
public class ControladorExcepciones {

    @ExceptionHandler({ExcepcionFormatoCorreoInvalido.class, ExcepcionFormatoClaveInvalida.class})
    public ResponseEntity<MensajeError> manejarExcepcionesValidacion(RuntimeException excepcion) {
        String error = excepcion.getMessage();
        log.error("Excepción de validación: {}", error, excepcion);
        return new ResponseEntity<>(new MensajeError(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ExcepcionCorreoDuplicado.class)
    public ResponseEntity<MensajeError> manejarExcepcionCorreoExistente(ExcepcionCorreoDuplicado excepcion) {
        String error = excepcion.getMessage();
        log.error("Excepción de correo ya registrado: {}", error, excepcion);
        return new ResponseEntity<>(new MensajeError(error), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<MensajeError> manejarExcepcionViolacionRestricciones(ConstraintViolationException excepcion) {
        String error = excepcion.getConstraintViolations().stream()
                .map(violacion -> violacion.getPropertyPath() + " " + violacion.getMessage())
                .collect(Collectors.joining(", "));
        log.error("Excepción de violación de restricciones: {}", error, excepcion);
        return new ResponseEntity<>(new MensajeError(error), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MensajeError> manejarExcepcionGeneral(Exception excepcion) {
        String error = excepcion.getMessage();
        log.error("Excepción : {}", error, excepcion);
        return new ResponseEntity<>(new MensajeError(error), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<MensajeError> manejarExcepcionMensajeHttpNoLeible(HttpMessageNotReadableException excepcion) {
        Throwable causa = excepcion.getCause();

        if (causa instanceof InvalidFormatException) {
            return manejarFormatoInvalido((InvalidFormatException) causa);
        }

        String error = excepcion.getMessage();
        log.error("Excepción: {}", error, excepcion);
        return new ResponseEntity<>(new MensajeError(error), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<MensajeError> manejarFormatoInvalido(InvalidFormatException excepcion) {
        String nombrePropiedad = excepcion.getPath().get(excepcion.getPath().size()-1).getFieldName();
        String valorPropiedad = excepcion.getValue().toString();
        String tipoEsperado = excepcion.getTargetType().getSimpleName();

        String mensajeRespuesta = String.format("La propiedad '%s': '%s' debe ser un %s", nombrePropiedad, valorPropiedad, tipoEsperado.toLowerCase());
        log.error("Excepción de serialización de JSON: {}", mensajeRespuesta, excepcion);
        return new ResponseEntity<>(new MensajeError(mensajeRespuesta), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<MensajeError> manejarExcepcionTransaccion(TransactionSystemException excepcion) {
        Throwable causa = excepcion.getRootCause();

        if (causa instanceof ConstraintViolationException) {
            return manejarExcepcionViolacionRestricciones((ConstraintViolationException) causa);
        }

        return manejarExcepcionGeneral(excepcion);
    }

}
