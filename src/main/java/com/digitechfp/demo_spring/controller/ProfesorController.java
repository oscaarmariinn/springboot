package com.digitechfp.demo_spring.controller;


import com.digitechfp.demo_spring.entity.Profesor;
import com.digitechfp.demo_spring.service.EstudianteService;
import com.digitechfp.demo_spring.service.ProfesorService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;
    private final EstudianteService estudianteService;

    public ProfesorController(ProfesorService profesorService, EstudianteService estudianteService) {
        this.profesorService = profesorService;
        this.estudianteService = estudianteService;
    }

    //LISTAR TODOS LOS PROFESORES

    @GetMapping
    public List<Profesor> obtenerTodos(){
        return profesorService.listarTodos();
    }

    //OBTENER POR ID

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return profesorService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("mensaje","El profesor no existe")));
    }

    //CREAR PROFESOR

    @PostMapping
    public ResponseEntity<?> crearProfesor(@RequestBody Profesor profesor){
        Profesor nuevoProfesor = profesorService.guardarProfesor(profesor);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProfesor);
    }

    //ACTUALIZAR COMPLETO

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCompleto(@PathVariable Long id, @RequestBody Profesor profesorDatos){

        return profesorService.buscarPorId(id)
                .<ResponseEntity<?>>map(profesorExistente -> {
                    profesorExistente.setNombreCompleto(profesorDatos.getNombreCompleto());
                    profesorExistente.setEspecialidad(profesorDatos.getEspecialidad());
                    profesorExistente.setExperienciaAnios(profesorDatos.getExperienciaAnios());

                    Profesor profesorActualizado = profesorService.guardarProfesor(profesorExistente);
                    return ResponseEntity.ok(profesorActualizado);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body( Collections.singletonMap("mensaje", "El profesor no existe")));
    }
    //ACTUALIZAR PARCIAL

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return profesorService.buscarPorId(id)
                .<ResponseEntity<?>>map(profesorExistente ->{
                    campos.forEach((campo, valor) -> {
                        switch (campo){
                            case "nombreCompleto":
                                profesorExistente.setNombreCompleto((String) valor);
                                break;
                            case "especialidad":
                                profesorExistente.setEspecialidad((String) valor);
                                break;
                            case "aniosExperiencia":
                                profesorExistente.setExperienciaAnios(((Number) valor).intValue());
                                break;
                        }
                    });
                    Profesor actualizado=
                            profesorService.guardarProfesor(profesorExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("mensaje", "El profesor no existe")));
    }

    //BORRAR

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProfesor(@PathVariable Long id){

        if(profesorService.buscarPorId(id).isPresent()){
            profesorService.eliminarProfesor(id);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "El profesor se elimino correctamente"));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("mensaje", "El profesor no existe"));

    }


}
