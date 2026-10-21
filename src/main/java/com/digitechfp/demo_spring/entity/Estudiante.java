package com.digitechfp.demo_spring.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<ContactoAuxiliar> contactosAuxiliares = new ArrayList<>();

    public Estudiante(String nombre, String correo, int edad, PerfilAcademico perfilAcademico) {

        this.nombre = nombre;
        this.correo = correo;
        this.edad = edad;
        this.perfilAcademico = perfilAcademico;
    }

    public void agregarContactoAuxiliar(ContactoAuxiliar contactoAuxiliar) {
        contactosAuxiliares.add(contactoAuxiliar);
        contactoAuxiliar.setEstudiante(this);
    }

    public void setContactosAuxiliares(List<ContactoAuxiliar> contactosAuxiliares) {
        this.contactosAuxiliares = contactosAuxiliares;
        if (this.contactosAuxiliares != null) {
            for (ContactoAuxiliar contactoAuxiliar : contactosAuxiliares) {
                contactoAuxiliar.setEstudiante(this);
            }
        }
    }
}
