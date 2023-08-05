package com.banco.apiusuarios.controlador;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.excepciones.DuplicateEmailException;
import com.banco.apiusuarios.servicio.UsuarioServicio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UsuarioControladorTest {

    @InjectMocks
    UsuarioControlador usuarioControlador;

    @Mock
    UsuarioServicio usuarioServicio;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void testCrearUsuario() {
        UsuarioCreationDto usuarioCreationDto = new UsuarioCreationDto("test@test.com", "TestUser", "Test1234");
        UsuarioResponseDto usuarioResponseDto = new UsuarioResponseDto(UUID.randomUUID(), null, null, null, null, true);

        when(usuarioServicio.crearUsuario(usuarioCreationDto)).thenReturn(usuarioResponseDto);

        ResponseEntity<UsuarioResponseDto> responseEntity = usuarioControlador.crearUsuario(usuarioCreationDto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

}
