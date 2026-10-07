package com.digitechfp.demo_spring.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "profesor")
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_completo", nullable = false)
    private String nombreCompleto;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "experiencia_anios")
    private int experienciaAnios;

    public Profesor(String nombreCompleto, String especialidad, int experienciaAnios) {
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.experienciaAnios = experienciaAnios;
    }
}
