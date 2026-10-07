package com.digitechfp.demo_spring.repository;

import com.digitechfp.demo_spring.entity.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
}
