package com.banco.apiusuarios.mapper;

import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.dto.UsuarioResponseDto;
import com.banco.apiusuarios.modelo.Telefono;
import com.banco.apiusuarios.modelo.Usuario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioMapper {

    public Usuario fromUsuarioCreationDto(UsuarioCreationDto usuarioCreationDto) {
        if (usuarioCreationDto != null) {
            Usuario usuario = Usuario.builder()
                    .email(usuarioCreationDto.getEmail())
                    .name(usuarioCreationDto.getName())
                    .password(usuarioCreationDto.getPassword())
                    .build();

            List<Telefono> telefonos = fromTelefonoDtoList(usuarioCreationDto.getPhones(), usuario);
            usuario.setPhones(telefonos);

            return usuario;
        } else {
            return new Usuario();
        }
    }

    public UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
        if (usuario != null) {
            return UsuarioResponseDto.builder()
                    .id(usuario.getId())
                    .created(usuario.getCreated())
                    .modified(usuario.getModified())
                    .lastLogin(usuario.getLastLogin())
                    .token(usuario.getToken())
                    .isActive(usuario.getIsActive())
                    .build();
        } else {
            return new UsuarioResponseDto();
        }
    }

    public List<Telefono> fromTelefonoDtoList(List<UsuarioCreationDto.TelefonoDto> telefonoDtos, Usuario usuario) {
        if (telefonoDtos != null){
            return telefonoDtos.stream()
                    .map(dto -> {
                        Telefono tel = new Telefono();
                        tel.setCityCode(dto.getCitycode());
                        tel.setCountryCode(dto.getCountrycode());
                        tel.setNumber(dto.getNumber());
                        tel.setUsuario(usuario);
                        return tel;
                    })
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }

    }
}
