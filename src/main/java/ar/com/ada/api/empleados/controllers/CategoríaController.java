package ar.com.ada.api.empleados.controllers;

import java.util.*;
import ar.com.ada.api.empleados.entities.*;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CategoríaController {

    @Autowired
    private CategoriaService service;

    @PostMapping("/categorias") //ningún web method devuelve void
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria) {

        GenericResponse respuesta = new GenericResponse();
        service.crearCategoria(categoria);

        respuesta.isOk = true;
        respuesta.id = categoria.getCategoriaId();
        respuesta.message = "La categoria fue creada con éxito";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/categorias") //hacer el mapping
    public ResponseEntity<List<Categoria>> traerCategorias() { //return Response Entity
        return ResponseEntity.ok(service.traerCategorias()); //return entity con el valor esperado

    }

    @GetMapping("/categorias/sueldos-nuevos")
    public ResponseEntity<List<Empleado>> calcularProximosSueldos() {
        return ResponseEntity.ok(service.calcularProximosSueldos());
    }

    @GetMapping("/categorias/sueldos-actuales")
    public ResponseEntity<List<Empleado>> obtenerSueldosActuales() {
        return ResponseEntity.ok(service.obtenerSueldosActuales());
    }

    @GetMapping("/categorias/sin-empleadas")
    public ResponseEntity<List<Categoria>> obtenerCategoriasSinEmpleados() {
        return ResponseEntity.ok(service.obtenerCategoriasSinEmpleados());
    }

    @GetMapping("/categorias/minimo-sueldo")
    public ResponseEntity<Categoria> obtenerCategoriaConMinimoSueldo() {
        return ResponseEntity.ok(service.obtenerCategoriaConMinimoSueldo());
    }

    @GetMapping("/categorias/nombres")
    public ResponseEntity<List<String>> obtenerNombresCategorias() {
        return ResponseEntity.ok(service.obtenerNombresCategorias());
    }
}
