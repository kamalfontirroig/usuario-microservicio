package com.banco.apiusuarios.repositorio;

import com.banco.apiusuarios.modelo.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {
    Usuario findByEmail(String email);
}
