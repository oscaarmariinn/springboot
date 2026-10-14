package com.digitechfp.demo_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "perfil_academico")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PerfilAcademico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "biografia", length = 250)
    private String biografia;

    @Column(name = "niver_ingles", length = 20)
    private String nivelIngles;

    public PerfilAcademico(String biografia, String nivelIngles) {
        this.biografia = biografia;
        this.nivelIngles = nivelIngles;
    }
}
