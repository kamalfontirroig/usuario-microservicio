package com.banco.apiusuarios.mapper;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.modelo.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public Usuario fromUsuarioCreationDto(UsuarioCreationDto usuarioCreationDto) {
        Usuario usuario = Usuario.builder()
        .email(usuarioCreationDto.getEmail())
        .name(usuarioCreationDto.getName())
        .password(usuarioCreationDto.getPassword())
                .build();
        return usuario;
    }

    public UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
        return UsuarioResponseDto.builder()
                .id(usuario.getId())
                .created(usuario.getCreated())
                .modified(usuario.getModified())
                .lastLogin(usuario.getLastLogin())
                .token(usuario.getToken())
                .isActive(usuario.getIsActive())
                .build();
    }
}