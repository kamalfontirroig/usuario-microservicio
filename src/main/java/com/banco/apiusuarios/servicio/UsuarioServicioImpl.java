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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UsuarioServicioImpl implements UsuarioServicio {

    private final UsuarioRepositorio usuarioRepositorio;
    private final UsuarioMapper usuarioMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UsuarioValidator usuarioValidator;
    private final JwtUtil jwtUtil;

    private static final Logger log = LoggerFactory.getLogger(UsuarioServicioImpl.class);

    @Autowired
    public UsuarioServicioImpl(UsuarioRepositorio usuarioRepositorio, UsuarioMapper usuarioMapper, BCryptPasswordEncoder passwordEncoder, UsuarioValidator usuarioValidator, JwtUtil jwtUtil) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.usuarioMapper = usuarioMapper;
        this.passwordEncoder = passwordEncoder;
        this.usuarioValidator = usuarioValidator;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UsuarioResponseDto crearUsuario(UsuarioCreationDto usuarioCreationDto) {

        log.info("Validando usuario");
        Usuario usuarioEnBD = usuarioRepositorio.findByEmail(usuarioCreationDto.getEmail());
        if (usuarioValidator.validarCreationUsuarioDto(usuarioCreationDto, usuarioEnBD)) {

        Usuario nuevoUsuario = usuarioMapper.fromUsuarioCreationDto(usuarioCreationDto);

        //Encriptamos su password
        log.info("Encriptando password");
        nuevoUsuario.setPassword(encryptPassword(nuevoUsuario.getPassword()));

        //Creamos un JWT
        log.info("Generando JWT");
        nuevoUsuario.setToken(jwtUtil.createToken(nuevoUsuario));
        Usuario usuarioGuardado = usuarioRepositorio.save(nuevoUsuario);

        log.info("Nuevo usuario guardado: " + usuarioGuardado);
        return usuarioMapper.toUsuarioResponseDto(usuarioGuardado);
        }
        return null;
    }

    private String encryptPassword(String rawPassword){
            return passwordEncoder.encode(rawPassword);
        }
}
