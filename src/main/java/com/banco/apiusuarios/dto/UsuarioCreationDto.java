package com.banco.apiusuarios.dto;

import lombok.*;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class UsuarioCreationDto {
    private String email;
    private String name;
    private String password;
    private List<TelefonoDto> phones;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TelefonoDto {
        private String citycode;
        private String countrycode;
        private String number;
    }
}
