package com.banco.apiusuarios.repositorio;

import com.banco.apiusuarios.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class UsuarioRepositorioTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @BeforeEach
    public void setUp() {
    }

    @Test
    public void testFindByEmail() {
        Usuario usuario = Usuario.builder()
            .email("test@test.com")
            .name("Test")
            .password("Test123")
            .build();

        entityManager.persist(usuario);
        entityManager.flush();

        Usuario found = usuarioRepositorio.findByEmail(usuario.getEmail());

        assertEquals(usuario.getEmail(), found.getEmail());
    }
}
