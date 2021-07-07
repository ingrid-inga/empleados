package ar.com.ada.api.empleados.controllers;

import java.util.*;
import ar.com.ada.api.empleados.entities.*;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.*;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
public class CategoríaController {

    @Autowired
    private CategoriaService service; 

    @PostMapping("/categorias")
    public ResponseEntity<?> crearCategoria(@RequestBody Categoria categoria){
        
        GenericResponse respuesta = new GenericResponse();
        service.crearCategoria(categoria);

        respuesta.isOk = true;
        respuesta.id = categoria.getCategoriaId();
        respuesta.message = "La categoria fue creada con exito";

        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/categorias")
    public ResponseEntity<List<Categoria>> traerCategorias() {
        return ResponseEntity.ok(service.traerCategorias());

    }
}
