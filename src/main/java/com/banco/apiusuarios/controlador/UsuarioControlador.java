package com.banco.apiusuarios.controlador;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.modelo.Usuario;
import com.banco.apiusuarios.servicio.UsuarioServicio;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioControlador {

    private final UsuarioServicio usuarioServicio;

    @Autowired
    public UsuarioControlador(UsuarioServicio usuarioServicio){
        this.usuarioServicio = usuarioServicio;
    }

    //Los requests malformados son manejados por la clase UsuarioControladorExcepciones
    @PostMapping
    public ResponseEntity<UsuarioResponseDto> crearUsuario(@RequestBody UsuarioCreationDto usuarioCreationDto){
        UsuarioResponseDto usuarioCreado = usuarioServicio.crearUsuario(usuarioCreationDto);
        return new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
    }
}