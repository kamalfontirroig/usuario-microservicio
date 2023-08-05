package com.banco.apiusuarios.servicio;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;


public interface UsuarioServicio {
    UsuarioResponseDto crearUsuario(UsuarioCreationDto usuario);
}
