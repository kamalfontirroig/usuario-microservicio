package com.banco.apiusuarios.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "patron")
@Data
public class PatronConfig {

    private String email;
    private String password;

}
