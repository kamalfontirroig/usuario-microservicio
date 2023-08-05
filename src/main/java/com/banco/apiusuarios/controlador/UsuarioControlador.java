package com.banco.apiusuarios.controlador;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.servicio.UsuarioServicio;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }


    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@RequestBody UsuarioCreationDto usuarioCreationDto){
        log.info("Iniciando creación de usuario con datos: { email: " + usuarioCreationDto.getEmail() + ", nombre: " + usuarioCreationDto.getName() );
        UsuarioResponseDto usuarioCreado = usuarioServicio.crearUsuario(usuarioCreationDto);
        log.info("Usuario creado exitosamente con Id: " + usuarioCreado.getId());
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }
}