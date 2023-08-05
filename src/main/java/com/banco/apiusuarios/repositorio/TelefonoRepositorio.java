package com.banco.apiusuarios.repositorio;

import com.banco.apiusuarios.modelo.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelefonoRepositorio extends JpaRepository<Telefono, UUID> {
}
