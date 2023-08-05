package com.banco.apiusuarios.modelo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@Data
@Entity
@Table(name = "telefonos")
public class Telefono {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Column(name = "number")
    private String number;

    @NotBlank
    @Column(name = "city_code")
    private String cityCode;

    @NotBlank
    @Column(name = "country_code")
    private String countryCode;

    @ManyToOne
    @JoinColumn(name="usuario_id", nullable=false)
    @ToString.Exclude
    private Usuario usuario;
}
