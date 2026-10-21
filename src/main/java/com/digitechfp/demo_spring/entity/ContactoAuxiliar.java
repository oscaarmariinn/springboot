package com.digitechfp.demo_spring.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contactoAuxiliar")
public class ContactoAuxiliar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "email")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_id", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Estudiante estudiante;

    public ContactoAuxiliar(String nombre, String telefono, String email, Estudiante estudiante) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.estudiante = estudiante;
    }
}
