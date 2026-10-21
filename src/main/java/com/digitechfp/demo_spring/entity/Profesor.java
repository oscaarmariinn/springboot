package com.digitechfp.demo_spring.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "profesor")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "oficina_id", referencedColumnName = "id")
    private Oficina oficina;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<Asignatura> asignaturas = new ArrayList<>();

    public Profesor(String nombreCompleto, String especialidad, int experienciaAnios, Oficina oficina) {
        this.nombreCompleto = nombreCompleto;
        this.especialidad = especialidad;
        this.experienciaAnios = experienciaAnios;
        this.oficina = oficina;
    }

    //METODO PARA AGREGAR LISTAS DE ASIGNATURAS

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.add(asignatura);
        asignatura.setProfesor(this);
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
        if (asignaturas != null) {
            for (Asignatura asignatura : asignaturas) {
                asignatura.setProfesor(this);
            }
        }
    }
}
