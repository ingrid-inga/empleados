package ar.com.ada.api.empleados.services;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.*;
import ar.com.ada.api.empleados.entities.Empleado.EstadoEmpleadoEnum;
import ar.com.ada.api.empleados.repos.EmpleadoRepository;
import ar.com.ada.api.empleados.repos.CategoriaRepository;

@Service
public class EmpleadoService {

    @Autowired

    EmpleadoRepository repo;

    @Autowired
    CategoriaService categoriaService;

    public void crearEmpleado(Empleado empleado){
        repo.save(empleado);
    }

    public List<Empleado> traerEmpleados(){
        return repo.findAll();
    }

//Se crea método para simplificar controller
    public Empleado buscarEmpleado(Integer empleadoId){
        Optional<Empleado> resultado = repo.findById(empleadoId);

        if (resultado.isPresent())
            return resultado.get();
        return null;

    }
     //DELETE LOGICO,que se mantiene en la db pero con estatus.
	public void bajaEmpleadoPorId(Integer id) {
        Empleado empleado = this.buscarEmpleado(id);

        empleado.setEstado(EstadoEmpleadoEnum.BAJA);
        empleado.setFechaBaja(new Date());

        repo.save(empleado);

	}

	public List<Empleado> traerEmpleadoPorCategoria(Integer catId) {

        Categoria categoria = categoriaService.buscarCategoria(catId);

        return categoria.getEmpleados();

    }
    
    public void guardar(Empleado empleado) {
        repo.save(empleado);
    }
    
}
