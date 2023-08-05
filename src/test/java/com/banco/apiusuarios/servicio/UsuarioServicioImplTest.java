package com.banco.apiusuarios.servicio;

import com.banco.apiusuarios.config.PatronConfig;
import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.excepciones.DuplicateEmailException;
import com.banco.apiusuarios.mapper.UsuarioMapper;
import com.banco.apiusuarios.modelo.Usuario;
import com.banco.apiusuarios.repositorio.UsuarioRepositorio;
import com.banco.apiusuarios.security.JwtUtil;
import com.banco.apiusuarios.util.UsuarioValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.support.hierarchical.ThrowableCollector;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class UsuarioServicioImplTest {

    @InjectMocks
    UsuarioServicioImpl usuarioServicio;

    @Mock
    UsuarioRepositorio usuarioRepositorio;

    @Mock
    UsuarioMapper usuarioMapper;

    @Mock
    BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UsuarioValidator usuarioValidator;

    @Mock
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(usuarioRepositorio.save(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario userToSave = invocation.getArgument(0);
            userToSave.setId(UUID.randomUUID());
            userToSave.setCreated(new Date());
            userToSave.setModified(new Date());
            userToSave.setLastLogin(new Date());
            userToSave.setIsActive(true);
            return userToSave;
        });

        //Mocking el mapper
        when(usuarioMapper.fromUsuarioCreationDto(any(UsuarioCreationDto.class))).thenAnswer(invocation -> {
            UsuarioCreationDto usuarioDto = invocation.getArgument(0);
            UsuarioMapper mapper = new UsuarioMapper();
            return mapper.fromUsuarioCreationDto(usuarioDto);
        });
        when(usuarioMapper.toUsuarioResponseDto(any(Usuario.class))).thenAnswer(invocation -> {
            Usuario usuario = invocation.getArgument(0);
            UsuarioMapper mapper = new UsuarioMapper();
            return mapper.toUsuarioResponseDto(usuario);
        });

        when(passwordEncoder.encode(anyString())).thenReturn("encryptedPassword");

        when(jwtUtil.createToken(any(Usuario.class))).thenReturn("ThisIsAToken");

    }

    @Test
    public void testCrearUsuario() {
        UsuarioCreationDto usuarioCreationDto = new UsuarioCreationDto("test123@test.com", "TestUser", "Test1234");
        when(usuarioValidator.validarCreationUsuarioDto(any(UsuarioCreationDto.class), eq(null))).thenReturn(true);
        when(usuarioRepositorio.findByEmail(any(String.class))).thenReturn(null);
        UsuarioResponseDto result = usuarioServicio.crearUsuario(usuarioCreationDto);

        assertNotNull(result.getToken());
        assertTrue(result.getIsActive());
    }



}
