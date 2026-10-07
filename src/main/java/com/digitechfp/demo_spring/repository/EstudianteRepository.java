package com.digitechfp.demo_spring.repository;

import com.digitechfp.demo_spring.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    boolean existsByCorreo(String correo);

    Estudiante findByCorreo(String correo);
}
