package com.digitechfp.demo_spring.service;


import com.digitechfp.demo_spring.entity.Estudiante;
import com.digitechfp.demo_spring.repository.EstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    // OBTENER TODOS LOS ESTUDIANTES

    public List<Estudiante> listarTodos(){
        return estudianteRepository.findAll();
    }

    //BUSCAR POR ID

    public Optional<Estudiante> buscarPorId(Long id){
        return estudianteRepository.findById(id);
    }

    //GUARDAR O ACTUALIZAR

    public Estudiante guardarEstudiante(Estudiante estudiante){
         return estudianteRepository.save(estudiante);
    }

    //BORRAR ESTUDIANTE

    public void eliminarEstudiante(Long id){
        estudianteRepository.deleteById(id);
    }

    //BUSCAR SI EXISTE UN CORREO

    public boolean existByCorreo(String correo){
        return estudianteRepository.existsByCorreo(correo);
    }

    //BUSCAR ESTUDIANTE POR CORREO

    public Estudiante buscarPorCorreo(String correo){
        return estudianteRepository.findByCorreo(correo);
    }


}
