package com.banco.apiusuarios.util;

import com.banco.apiusuarios.config.PatronConfig;
import com.banco.apiusuarios.dto.UsuarioCreationDto;
import com.banco.apiusuarios.excepciones.DuplicateEmailException;
import com.banco.apiusuarios.excepciones.InvalidEmailFormat;
import com.banco.apiusuarios.excepciones.InvalidPasswordFormat;
import com.banco.apiusuarios.modelo.Usuario;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
public class UsuarioValidator {

    private final PatronConfig patronConfig;

    private final Pattern EMAIL_PATTERN;
    private final Pattern PASSWORD_PATTERN;

    private final Logger log = LoggerFactory.getLogger(UsuarioValidator.class);

    @Autowired
    public UsuarioValidator(PatronConfig patronConfig){
        this.patronConfig = patronConfig;

        EMAIL_PATTERN = Pattern.compile(this.patronConfig.getEmail());
        log.info("Compilado patron Email: " + EMAIL_PATTERN.pattern());

        PASSWORD_PATTERN = Pattern.compile(this.patronConfig.getPassword());
        log.info("Compilado patron Password: " + PASSWORD_PATTERN.pattern());
    }

    public boolean validarCreationUsuarioDto(UsuarioCreationDto usuarioCreationDto, Usuario usuarioEnBD){
        log.info("Validando Formato de Email: " + usuarioCreationDto.getEmail());
        Matcher emailMatcher = EMAIL_PATTERN.matcher(usuarioCreationDto.getEmail());
        if (!emailMatcher.matches()) {
            throw new InvalidEmailFormat("Correo electrónico inválido");
        }

        log.info("Validando Email No Registrado");
        if (usuarioEnBD != null) {
            throw new DuplicateEmailException("El correo ya está registrado");
        }

        log.info("Validando Password");
        Matcher passwordMatcher = PASSWORD_PATTERN.matcher(usuarioCreationDto.getPassword());
        if (!passwordMatcher.matches()) {
            throw new InvalidPasswordFormat("La clave es inválida");
        }
        return true;
    }

    public boolean validarCreationUsuarioDto(UsuarioCreationDto usuarioCreationDto){
        return validarCreationUsuarioDto(usuarioCreationDto, null);
    }

}
