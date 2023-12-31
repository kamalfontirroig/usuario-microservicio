package com.banco.apiusuarios.util;

import com.banco.apiusuarios.config.UsuarioValidatorPatronesConfig;
import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.excepciones.ExcepcionCorreoDuplicado;
import com.banco.apiusuarios.excepciones.ExcepcionFormatoCorreoInvalido;
import com.banco.apiusuarios.excepciones.ExcepcionFormatoClaveInvalida;
import com.banco.apiusuarios.modelo.Usuario;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
@Log4j2
public class UsuarioValidator {

    private final UsuarioValidatorPatronesConfig patronConfig;

    private final Pattern EMAIL_PATTERN;
    private final Pattern PASSWORD_PATTERN;

    @Autowired
    public UsuarioValidator(UsuarioValidatorPatronesConfig patronConfig){
        this.patronConfig = patronConfig;

        EMAIL_PATTERN = Pattern.compile(this.patronConfig.getEmail());
        log.info("Patrón compilado, Email: {}", EMAIL_PATTERN.pattern());

        PASSWORD_PATTERN = Pattern.compile(this.patronConfig.getPassword());
        log.info("Patrón compilado, Contraseña: {}", PASSWORD_PATTERN.pattern());
    }

    public boolean validarCreationUsuarioDto(UsuarioCreationDto usuarioCreationDto, Usuario usuarioEnBD){
        log.info("Validando el formato del correo: {}", usuarioCreationDto.getEmail());
        Matcher emailMatcher = EMAIL_PATTERN.matcher(usuarioCreationDto.getEmail());
        if (!emailMatcher.matches()) {
            throw new ExcepcionFormatoCorreoInvalido("Correo electrónico inválido");
        }

        log.info("Validando que el correo {} no esté registrado", usuarioCreationDto.getEmail());
        if (usuarioEnBD != null) {
            throw new ExcepcionCorreoDuplicado("El correo ya está registrado");
        }

        log.info("Validando el formato de la contraseña");
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(usuarioCreationDto.getPassword());
        if (!passwordMatcher.matches()) {
            throw new ExcepcionFormatoClaveInvalida("La clave es inválida");
        }
        return true;
    }

    public boolean validarCreationUsuarioDto(UsuarioCreationDto usuarioCreationDto){
        return validarCreationUsuarioDto(usuarioCreationDto, null);
    }
}
