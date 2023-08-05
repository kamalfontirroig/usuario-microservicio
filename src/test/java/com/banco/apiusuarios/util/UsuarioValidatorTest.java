package com.banco.apiusuarios.util;

import com.banco.apiusuarios.config.PatronConfig;
import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.excepciones.DuplicateEmailException;
import com.banco.apiusuarios.excepciones.InvalidEmailFormat;
import com.banco.apiusuarios.excepciones.InvalidPasswordFormat;
import com.banco.apiusuarios.modelo.Usuario;
import com.banco.apiusuarios.repositorio.UsuarioRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UsuarioValidatorTest {

    @Autowired
    private UsuarioValidator usuarioValidator;

    @Test
    public void testValidarCreationUsuarioDtoHappyPath() {
        UsuarioCreationDto dto = new UsuarioCreationDto();
        dto.setEmail("test@test.com");
        dto.setPassword("Password01");
        assertTrue(usuarioValidator.validarCreationUsuarioDto(dto));
    }

    @Test
    public void testValidarCreationUsuarioDtoInvalidEmail() {
        UsuarioCreationDto dto = new UsuarioCreationDto();
        dto.setEmail("invalidEmail");
        dto.setPassword("aPssword01");
        Exception exception = assertThrows(InvalidEmailFormat.class, () -> {
            usuarioValidator.validarCreationUsuarioDto(dto);
        });
        assertEquals("Correo electrónico inválido", exception.getMessage());
    }

    @Test
    public void testValidarCreationUsuarioDtoInvalidPassword() {
        UsuarioCreationDto dto = new UsuarioCreationDto();
        dto.setEmail("test1@test.com");
        dto.setPassword("password");
        Exception exception = assertThrows(InvalidPasswordFormat.class, () -> {
            usuarioValidator.validarCreationUsuarioDto(dto);
        });
        assertEquals("La clave es inválida", exception.getMessage());
    }

    @Test
    public void testCrearUsuarioWithExistingEmail() {
        UsuarioCreationDto usuarioCreationDto = new UsuarioCreationDto("test@test.com", "test", "test123");
        Usuario usuario = Usuario.builder()
                .email("test@test.com")
                .name("test")
                .password("test123")
                .build();
        assertThrows(DuplicateEmailException.class, () -> usuarioValidator.validarCreationUsuarioDto(usuarioCreationDto, usuario));
    }
}