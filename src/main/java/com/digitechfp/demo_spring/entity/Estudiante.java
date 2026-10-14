package com.digitechfp.demo_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "estudiante")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 100)
    private String correo;

    @Column(nullable = false)
    private int edad;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "perfil_id", referencedColumnName = "id")
    private PerfilAcademico perfilAcademico;

    public Estudiante(String nombre, String correo, int edad, PerfilAcademico perfilAcademico) {

        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.perfilAcademico = perfilAcademico;
    }
}
