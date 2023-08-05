package com.banco.apiusuarios.excepciones;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ControladorDeExcepcionesTest {

    @InjectMocks
    private UsuarioControladorExcepciones controladorDeExcepciones;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testManejarExcepcionesDeValidacionEmail() {
        IllegalArgumentException exception = new IllegalArgumentException("Correo electrónico inválido");
        ResponseEntity<ErrorResponse> response = controladorDeExcepciones.manejarExcepcionesDeValidacion(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Correo electrónico inválido", response.getBody().getMensaje());
    }

    @Test
    void testManejarExcepcionesDeValidacionPassword() {
        IllegalArgumentException exception = new IllegalArgumentException("La clave es inválida");
        ResponseEntity<ErrorResponse> response = controladorDeExcepciones.manejarExcepcionesDeValidacion(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("La clave es inválida", response.getBody().getMensaje());
    }

    @Test
    void testManejarDuplicateEmailException() {
        DuplicateEmailException exception = new DuplicateEmailException("El correo ya está registrado");
        ResponseEntity<ErrorResponse> response = controladorDeExcepciones.manejarExcepcionDeCorreoExistente(exception);

        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals("El correo ya está registrado", response.getBody().getMensaje());
    }
}
