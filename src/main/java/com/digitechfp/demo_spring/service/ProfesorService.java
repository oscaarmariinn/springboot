package com.digitechfp.demo_spring.service;


import com.digitechfp.demo_spring.entity.Profesor;
import com.digitechfp.demo_spring.repository.ProfesorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public ProfesorService(ProfesorRepository profesorRepository) {
        this.profesorRepository = profesorRepository;
    }

    //OBTENER TODOS LOS PROFESORES

    public List<Profesor> listarTodos(){
        return profesorRepository.findAll();
    }

    //BUSCAR POR ID

    public Optional<Profesor> buscarPorId(Long id){
        return profesorRepository.findById(id);
    }

    //GUARDAR O ACTUALIZAR

    public Profesor guardarProfesor(Profesor profesor){
        return profesorRepository.save(profesor);
    }

    //BORRAR PROFESOR

    public void eliminarProfesor(Long id){
        profesorRepository.deleteById(id);
    }

}
