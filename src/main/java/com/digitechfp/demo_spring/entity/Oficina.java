package com.digitechfp.demo_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oficina")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Oficina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_oficina")
    private String codigoOficina;

    @Column(name = "edificio")
    private String edificio;

    public Oficina(String codigoOficina, String edificio) {
        this.codigoOficina = codigoOficina;
        this.edificio = edificio;
    }
}
