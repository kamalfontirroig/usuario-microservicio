package com.banco.apiusuarios.excepciones;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ControladorDeExcepcionesTest {

    @InjectMocks
    private ControladorGlobalExcepciones controladorDeExcepciones;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testManejarExcepcionesDeValidacionEmail() {
        InvalidEmailFormat exception = new InvalidEmailFormat("Correo electrónico inválido");
        ResponseEntity<ErrorResponseMessage> response = controladorDeExcepciones.manejarExcepcionesValidacion(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Correo electrónico inválido", response.getBody().getMensaje());
    }

    @Test
    void testManejarExcepcionesDeValidacionPassword() {
        InvalidPasswordFormat exception = new InvalidPasswordFormat("La clave es inválida");
        ResponseEntity<ErrorResponseMessage> response = controladorDeExcepciones.manejarExcepcionesValidacion(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La clave es inválida", response.getBody().getMensaje());
    }

    @Test
    void testManejarDuplicateEmailException() {
        DuplicateEmailException exception = new DuplicateEmailException("El correo ya está registrado");
        ResponseEntity<ErrorResponseMessage> response = controladorDeExcepciones.manejarExcepcionCorreoExistente(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El correo ya está registrado", response.getBody().getMensaje());
    }

    @Test
    void testManejarExcepcionViolacionRestricciones() {
        Set<ConstraintViolation<?>> violations = new HashSet<>();
        ConstraintViolation<?> violation = mock(ConstraintViolation.class);
        when(violation.getPropertyPath()).thenReturn(PathImpl.createPathFromString("field"));
        when(violation.getMessage()).thenReturn("error message");
        violations.add(violation);

        ConstraintViolationException exception = new ConstraintViolationException(violations);
        ResponseEntity<ErrorResponseMessage> response = controladorDeExcepciones.manejarExcepcionViolacionRestricciones(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("field error message", response.getBody().getMensaje());
    }


    @Test
    void testManejarExcepcionFormatoInvalido() {
        JsonMappingException.Reference ref = new JsonMappingException.Reference(new Object(), "field");
        List<JsonMappingException.Reference> path = Collections.singletonList(ref);
        InvalidFormatException exception = new InvalidFormatException((JsonParser) null, "error message", "invalid value", Integer.class);
        exception.prependPath(ref);

        ResponseEntity<ErrorResponseMessage> response = controladorDeExcepciones.manejarExcepcionFormatoInvalido(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La propiedad 'field': 'invalid value' debe ser un integer", response.getBody().getMensaje());
    }

}

