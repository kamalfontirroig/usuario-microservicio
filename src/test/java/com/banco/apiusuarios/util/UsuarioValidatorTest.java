package com.banco.apiusuarios.util;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.excepciones.ExcepcionCorreoDuplicado;
import com.banco.apiusuarios.excepciones.ExcepcionFormatoClaveInvalida;
import com.banco.apiusuarios.excepciones.ExcepcionFormatoCorreoInvalido;
import com.banco.apiusuarios.modelo.Usuario;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

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
        Exception exception = assertThrows(ExcepcionFormatoCorreoInvalido.class, () -> {
            usuarioValidator.validarCreationUsuarioDto(dto);
        });
        assertEquals("Correo electrónico inválido", exception.getMessage());
    }

    @Test
    public void testValidarCreationUsuarioDtoInvalidPassword() {
        UsuarioCreationDto dto = new UsuarioCreationDto();
        dto.setEmail("test1@test.com");
        dto.setPassword("password");
        Exception exception = assertThrows(ExcepcionFormatoClaveInvalida.class, () -> {
            usuarioValidator.validarCreationUsuarioDto(dto);
        });
        assertEquals("La clave es inválida", exception.getMessage());
    }

    @Test
    public void testCrearUsuarioWithExistingEmail() {
        UsuarioCreationDto usuarioCreationDto = UsuarioCreationDto.builder().email("test@test.com").name("test").password("test123").build();
        Usuario usuario = Usuario.builder()
                .email("test@test.com")
                .name("test")
                .password("test123")
                .build();
        assertThrows(ExcepcionCorreoDuplicado.class, () -> usuarioValidator.validarCreationUsuarioDto(usuarioCreationDto, usuario));
    }
}
