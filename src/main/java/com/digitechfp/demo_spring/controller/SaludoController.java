package com.digitechfp.demo_spring.controller;

import com.digitechfp.demo_spring.dto.InfoDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SaludoController {

    @GetMapping("/saludo")
    public String saludar(@RequestParam(value = "nombre", defaultValue = "Mundo") String nombre){
        return "¡Hola, "+ nombre + "! Bienvenido a la clase de Spring Boot de DAM.";
    }

    @GetMapping("/info")
    public InfoDTO info(){
        return new InfoDTO("2 DAM", "Digitech", "Francisco Belda");
    }

    @GetMapping("suma")
    public String sumar(@RequestParam(value = "a") int a, @RequestParam(value = "b") int b){
        int resultado = a + b;
        return "El resultado de la suma de " + a + " + " + b + " = " + resultado;
    }

}
