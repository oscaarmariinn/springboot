package com.digitechfp.demo_spring.config;

import com.digitechfp.demo_spring.entity.Estudiante;
import com.digitechfp.demo_spring.entity.Profesor;
import com.digitechfp.demo_spring.repository.EstudianteRepository;
import com.digitechfp.demo_spring.repository.ProfesorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration

public class InicializadorDatos {
    @Bean
    CommandLineRunner initData(EstudianteRepository estudianteRepository, ProfesorRepository profesorRepository){
        return args -> {
            //INSERTAR ESTUDIANTES
            Estudiante estudiante1 = new Estudiante("Ana Garcia", "ana.garcia@gmail.com", 19);
            Estudiante estudiante2 = new Estudiante("Pedro Martinez", "pedro.martinez@gmail.com", 18);
            Estudiante estudiante3 = new Estudiante("Oscar Marin", "oscar.marin@gmail.com", 20);

            estudianteRepository.save(estudiante1);
            estudianteRepository.save(estudiante2);
            estudianteRepository.save(estudiante3);

            System.out.println(">>>> ESTUDIANTES GUARDADOS CORRECTAMENTE EN LA BASE DE DATOS" );

            //CONSULTAR DATOS DE LA BD
            System.out.println(">>>>>LISTADO COMPLETO DE ESTUDIANTES");
            for ( Estudiante e: estudianteRepository.findAll()){
                System.out.println("- ID: "+e.getId()+" - Nombre: "+e.getNombre().toUpperCase()+" - Correo: "+e.getCorreo().toUpperCase()+" - Edad "+e.getEdad());
            }

            //CONSULTAR UN ESTUDIANTE
            System.out.println(">>>>>BUSCAMOS POR ID");
            Optional<Estudiante> e = estudianteRepository.findById(3L);
            e.ifPresent(estudiante ->
                    System.out.println(" -ID: "+estudiante.getId()+" -NOMBRE: "+estudiante.getNombre().toUpperCase()+" -CORREO: "+estudiante.getCorreo().toUpperCase()+" -EDAD: "+estudiante.getEdad()));


            //MODIFICAR DATOS

            System.out.println("\n >>>>ACTUALIZAMOS EL ID 2");
            Estudiante estudianteEncontrado = estudianteRepository.findById(2L).orElseThrow();

            estudianteEncontrado.setNombre("David Maria Jardinero");
            estudianteEncontrado.setCorreo("david.maria.jardinero@gmail.com");
            estudianteEncontrado.setEdad(20);

            estudianteRepository.save(estudianteEncontrado);

            System.out.println(">>> El resultado es:");
            System.out.println("Actualizado: "+estudianteRepository.findById(2L).orElse(null));

            //CONTAR LOS ESTUDIANTES

            System.out.println(">>>>>CONTAMOS LOS ESTUDIANTES");
            System.out.println("El numero total de estudiantes es: "+estudianteRepository.count());

            //BORRAR LOS ESTUDIANTES
            System.out.println(">>>> BORRAMOS EL ID 2");
            estudianteRepository.deleteById(2L);

            System.out.println(">>>>BORRADO CORRECTAMENTE");
            System.out.println(">>>>LISTADO FINAL: ");
            for (Estudiante est: estudianteRepository.findAll()){
                System.out.println("\n -ID: "+est.getId()+" -NOMBRE: "+est.getNombre()+" -CORREO: "+est.getCorreo()+" -EDAD "+est.getEdad());
            }



            //TAREA 3 PROFESORES

            //AÑADIR PROFESORES

            Profesor profesor1 = new Profesor("Juan Perez Rodriguez", "Desarrollo de Aplicaciones Web", 8);
            Profesor profesor2 = new Profesor("Lucia Del Toro Igual", "Ingles", 4);
            Profesor profesor3 = new Profesor("Rodrigo Galvez Martinez", "Matematicas Aplicadas", 19);

            profesorRepository.save(profesor1);
            profesorRepository.save(profesor2);
            profesorRepository.save(profesor3);
            System.out.println(">>>> PROFESORES GUARDADOS CORRECTAMENTE EN LA BASE DE DATOS" );

            System.out.println(">>>>>LISTADO COMPLETO DE PROFESORES");
            for ( Profesor p: profesorRepository.findAll()){
                System.out.println("\n- ID: "+p.getId()+" - NOMBRE COMPLETO: "+p.getNombreCompleto()+" - ESPECIALIDAD: "+p.getEspecialidad()+" - AÑOS DE EXPERIENCIA: "+p.getExperienciaAnios());
            }

            //ACTUALIZAR PROFESORES

            System.out.println("\n >>>>ACTUALIZAMOS EL PROFESOR CON ID 2");

            Profesor profesorEncontrado = profesorRepository.findById(2L).orElseThrow();
            profesorEncontrado.setNombreCompleto("Maria Del Caño Calle");
            profesorEncontrado.setEspecialidad("Lengua Castellana y Literatura");
            profesorEncontrado.setExperienciaAnios(1);

            profesorRepository.save(profesorEncontrado);

            System.out.println(">>> El resultado es:");
            System.out.println("Actualizado: "+profesorRepository.findById(2L).orElse(null));


            //BORRAR PROFESOR

            System.out.println("\n >>>>BORRAMOS AL PROFESOR CON ID 2");
            profesorRepository.deleteById(2L);

            System.out.println(">>>>BORRADO CORRECTAMENTE");
            System.out.println(">>>>LISTADO FINAL: ");
            for (Profesor prof: profesorRepository.findAll()){
                System.out.println("\n -ID: "+prof.getId()+" -PROFESOR: "+prof.getNombreCompleto()+" -ESPECIALIDAD: "+prof.getEspecialidad()+" -AÑOS DE EXPERIENCIA "+prof.getExperienciaAnios());
            }

        };
    }
}
