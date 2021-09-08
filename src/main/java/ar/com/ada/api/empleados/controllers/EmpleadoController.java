package ar.com.ada.api.empleados.controllers;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.entities.Empleado.EstadoEmpleadoEnum;
import ar.com.ada.api.empleados.models.request.InfoEmpleadoNuevo;
import ar.com.ada.api.empleados.models.request.SueldoActualizado;
import ar.com.ada.api.empleados.models.response.GenericResponse;
import ar.com.ada.api.empleados.services.CategoriaService;
import ar.com.ada.api.empleados.services.EmpleadoService;

@RestController
public class EmpleadoController {

    @Autowired
    private EmpleadoService service;

    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> traerEmpleados() {
        final List<Empleado> lista = service.traerEmpleados();
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/empleados")
    public ResponseEntity<?> crearEmpleado(@RequestBody InfoEmpleadoNuevo empleadoInfo) {
        GenericResponse respuesta = new GenericResponse();

        Empleado empleado = new Empleado();
        empleado.setNombre(empleadoInfo.nombre);
        empleado.setEdad(empleadoInfo.edad);
        empleado.setSueldo(empleadoInfo.sueldo);
        empleado.setFechaAlta(new Date());

        Categoria categoria = categoriaService.buscarCategoria(empleadoInfo.categoriaId);
        empleado.setCategoria(categoria);
        empleado.setEstado(EstadoEmpleadoEnum.ACTIVO);

        service.crearEmpleado(empleado);
        respuesta.isOk = true;
        respuesta.id = empleado.getEmpleadoId();
        respuesta.message = "El empleado fue creada con exito";
        return ResponseEntity.ok(respuesta);

    }

    @GetMapping("/empleados/{id}")
    public ResponseEntity<?> getEmpleadoPorId(@PathVariable Integer id) {
        Empleado empleado = service.buscarEmpleado(id);
        if (empleado == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(empleado);
    }

    // Detele/empleados/{id} --> Da de baja un empleado poniendo el campo estado en
    // "baja"
    // y la fecha de baja que sea el dia actual.
    @DeleteMapping("/empleados/{id}")
    public ResponseEntity<?> bajaEmpleado(@PathVariable Integer id) {

        service.bajaEmpleadoPorId(id);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "El empleado fue dado de baja con éxito";

        return ResponseEntity.ok(respuesta);

    }

    // Get /empleados/categorias/{catId} --> Obtiene la lista de empleados de una
    // categoria.
    @GetMapping("/empleados/categorias/{catId}")
    public ResponseEntity<List<Empleado>> obtenerEmpleadosPorCategoria(@PathVariable Integer catId) {

        List<Empleado> empleados = service.traerEmpleadoPorCategoria(catId);
        return ResponseEntity.ok(empleados);
    }

    @PutMapping("/empleados/{id}/sueldos")
    public ResponseEntity<GenericResponse> actualizarSueldo(@PathVariable Integer id,
            @RequestBody SueldoActualizado sueldoNuevoInfo) {
       
        // 1.-busca el empleado
        Empleado empleado = service.buscarEmpleado(id);
        // 2.-setear el sueldo nuevo
        empleado.setSueldo(sueldoNuevoInfo.sueldoNuevo);
        // 3.-se guarda en la base de datos
        service.guardar(empleado);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "Sueldo actualizado con éxito"; //+ empleado.getEmpleadoId();
        return ResponseEntity.ok(respuesta);

    }

}
