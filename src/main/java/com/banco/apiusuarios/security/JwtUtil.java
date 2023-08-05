package com.banco.apiusuarios.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.banco.apiusuarios.modelo.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {


    private String SECRET;
    private int EXPIRATION_TIME;

    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SECRET = secret;
    }

    @Value("${jwt.expiration}")
    public void setExpiration(Integer expiration) {
        EXPIRATION_TIME = expiration;
    }

    public String createToken(Usuario usuario) {
        return JWT.create()
            .withSubject(usuario.getEmail())
            .withExpiresAt(new Date(System.currentTimeMillis() + this.EXPIRATION_TIME * 1000))
            .sign(Algorithm.HMAC512(this.SECRET));
    }
}