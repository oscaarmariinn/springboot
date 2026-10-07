package com.digitechfp.demo_spring.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor


@Entity
@Table(name = "administrador")
public class Administrador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "anio_inicial")
    private Integer anioInicial;

    public Administrador(String nombreCompleto, String departamento, Integer anioInicial) {
        this.nombreCompleto = nombreCompleto;
        this.departamento = departamento;
        this.anioInicial = anioInicial;
    }
}
