package com.banco.apiusuarios.dto;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UsuarioCreationDto {
    private String email;
    private String name;
    private String password;
}