package com.digitechfp.demo_spring.controller;


import com.digitechfp.demo_spring.entity.Estudiante;
import com.digitechfp.demo_spring.service.EstudianteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    // LISTAR TODOS LOS ESTUDIANTES

    @GetMapping
    public List<Estudiante> obtenerTodos(){
        return estudianteService.listarTodos();
    }

    //BUSCAR POR ID

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return estudianteService.buscarPorId(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
        .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("mensaje","El estudiante no existe")));
    }

    //CREAR UN NUEVO ESTUDIANTE

    @PostMapping
    public ResponseEntity<?> crearEstudiante(@RequestBody Estudiante estudiante){
        //VALIDAMOS SI EL EMAIL ESTA REGISTRADO
        if(estudianteService.existByCorreo(estudiante.getCorreo())){
            return  ResponseEntity.status(HttpStatus.CONFLICT)
                    .body((Collections.singletonMap("mensaje", "El correo ya esta registrado por otro estudiante")));

        }
        //SI EL CORREO NO ESTA REGISTRADO GUARDAMOS EL ESTUDIANTE
        Estudiante nuevoEstudiante = estudianteService.guardarEstudiante(estudiante);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoEstudiante);


    }

    //ACTUALIZAR COMPLETO

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCompleto(@PathVariable Long id, @RequestBody Estudiante estudianteDatos){

        return estudianteService.buscarPorId(id)
                .<ResponseEntity<?>>map(estudianteExistente -> {
                    estudianteExistente.setNombre(estudianteDatos.getNombre());
                    estudianteExistente.setEdad(estudianteDatos.getEdad());
                    estudianteExistente.setCorreo(estudianteDatos.getCorreo());

                    Estudiante estudianteActualizado = estudianteService.guardarEstudiante(estudianteExistente);
                    return ResponseEntity.ok(estudianteActualizado);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("mensaje", "El estudiante no existe")));

    }

    //ACTUALIZAR PARCIAL

    @PatchMapping("/{id}")
    public ResponseEntity<?> actualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos){
        return estudianteService.buscarPorId(id)
                .<ResponseEntity<?>>map(estudianteExistente ->{
                    campos.forEach((campo, valor) -> {
                        switch (campo){
                            case "nombre":
                                estudianteExistente.setNombre((String) valor);
                                break;
                            case "edad":
                                estudianteExistente.setEdad(((Number) valor).intValue());
                                break;
                            case "correo":
                                estudianteExistente.setCorreo((String) valor);
                                break;
                        }
                    });
                    Estudiante actualizado=
                            estudianteService.guardarEstudiante(estudianteExistente);
                    return ResponseEntity.ok(actualizado);
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("mensaje", "El estudiante no existe")));
    }

    //BORRAR

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarEstudiante(@PathVariable Long id){

        if(estudianteService.buscarPorId(id).isPresent()){
            estudianteService.eliminarEstudiante(id);
            return ResponseEntity.ok(Collections.singletonMap("mensaje", "El estudiante se elimino correctamente"));

        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap("mensaje", "El estudiante no existe"));

    }
}
