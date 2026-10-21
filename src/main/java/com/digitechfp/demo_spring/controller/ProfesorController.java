package com.digitechfp.demo_spring.controller;


import com.digitechfp.demo_spring.entity.Asignatura;
import com.digitechfp.demo_spring.entity.Oficina;
import com.digitechfp.demo_spring.entity.PerfilAcademico;
import com.digitechfp.demo_spring.entity.Profesor;
import com.digitechfp.demo_spring.service.EstudianteService;
import com.digitechfp.demo_spring.service.ProfesorService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
                    if(profesorDatos.getOficina() != null){
                        if(profesorExistente.getOficina() != null){
                            profesorExistente.getOficina().setCodigoOficina(
                                    profesorDatos.getOficina().getCodigoOficina()
                            );
                            profesorExistente.getOficina().setEdificio(
                                    profesorDatos.getOficina().getEdificio()
                            );
                        }else {
                            profesorExistente.setOficina(null);
                        }
                    }
                    profesorExistente.setAsignaturas(profesorDatos.getAsignaturas() );


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
                            case "oficina":
                                if (valor instanceof Map){
                                    @SuppressWarnings("unchecked")
                                    Map<String, Object> datosOficina =
                                            (Map<String, Object>)valor;

                                    Oficina oficina =
                                            profesorExistente.getOficina();
                                    if (oficina == null){
                                        oficina = new Oficina();
                                        profesorExistente.setOficina(oficina);
                                    }
                                    if (datosOficina.containsKey("codigoOficina")){
                                        oficina.setCodigoOficina((String) datosOficina.get("codigoOficina"));
                                    }
                                    if (datosOficina.containsKey("edificio")){
                                        oficina.setEdificio((String) datosOficina.get("edificio"));
                                    }
                                } else if (valor == null){
                                    profesorExistente.setOficina(null);
                                }
                                break;
                            case "asignaturas":
                                if (valor instanceof List){
                                    @SuppressWarnings("uncheked")
                                            List<Map<String, Object>> listasAsignaturasMap = (List<Map<String, Object>>) valor;
                                    List<Asignatura> asignaturas = new ArrayList<>();
                                    for (Map<String, Object> mapAsignatura : listasAsignaturasMap){
                                        Asignatura asignatura = new Asignatura();
                                        if (mapAsignatura.containsKey("id") && mapAsignatura.get("id") != null){
                                            asignatura.setId(((Number) mapAsignatura.get("id")).longValue());
                                        }
                                        asignatura.setNombre((String) mapAsignatura.get("nombre"));
                                        if (mapAsignatura.get("creditos") instanceof Number){
                                            asignatura.setCreditos(((Number) mapAsignatura.get("creditos")).intValue());
                                        }
                                        asignaturas.add(asignatura);
                                    }
                                    profesorExistente.setAsignaturas(asignaturas);
                                }
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
