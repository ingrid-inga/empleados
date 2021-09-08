package ar.com.ada.api.empleados.services;

import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
import ar.com.ada.api.empleados.entities.Empleado;
import ar.com.ada.api.empleados.repos.CategoriaRepository;
import ar.com.ada.api.empleados.repos.EmpleadoRepository;

@Service
public class CategoriaService {

    @Autowired
    CategoriaRepository repo;

    public void crearCategoria(Categoria categoria) {
        repo.save(categoria);

    }

    public List<Categoria> traerCategorias() {
        return repo.findAll();
    }

    public Categoria buscarCategoria(Integer categoriaId) {
        Optional<Categoria> resultado = repo.findById(categoriaId);
        Categoria categoria = null;

        if (resultado.isPresent())
            categoria = resultado.get();
        return categoria;
    }

    public Categoria findCategoria(String categoriaNombre){
        return repo.findByNombre(categoriaNombre);
    }

    //buscar una categoria por id y eliminarla

    public void eliminarCategoria(Integer categoriaId){
        repo.deleteById(categoriaId);
    }

        /// Administrativa: proximo sueldo es el sueldo base de la categoria si el
    /// sueldo esta por debajo de la categroia
    // Ventas: proximo sueldo es el sueldo base de categorias + 10% ventas anuales
    // Auxiliar: siempre cobra el sueldo base de la categoria.
    public List<Empleado> calcularProximosSueldos() {
        List<Empleado> listaEmpleados = new ArrayList<>();

        // Uso de streams: flujo de objetos
        this.traerCategorias().stream().forEach(categoria -> {
            categoria.getEmpleados().stream().forEach(empleado -> {
                empleado.setSueldo(categoria.calcularProximoSueldo(empleado));
                listaEmpleados.add(empleado);
            });
        });

        return listaEmpleados;
    }

    public List<Empleado> calcularProximosSueldosOldWay() {
        List<Empleado> listaEmpleados = new ArrayList<>();

        for (Categoria categoria : this.traerCategorias()) {
            for (Empleado empleado : categoria.getEmpleados()) {
                empleado.setSueldo(categoria.calcularProximoSueldo(empleado));
                listaEmpleados.add(empleado);
            }
        }

        return listaEmpleados;
    }

    public List<Empleado> obtenerSueldosActuales() {
        List<Empleado> listaEmpleados = new ArrayList<>();

        traerCategorias().stream().forEach(cat -> listaEmpleados.addAll(cat.getEmpleados()));

        return listaEmpleados;
    }

    public List<Empleado> obtenerSueldosActualesNoStream() {
        List<Empleado> listaEmpleados = new ArrayList<>();

        for (Categoria categoria : this.traerCategorias()) {
            for (Empleado empleado : categoria.getEmpleados()) {
                listaEmpleados.add(empleado);
            }
        }

        return listaEmpleados;
    }

    public List<Categoria> obtenerCategoriasSinEmpleados() {
        return traerCategorias().stream().filter(categoria -> categoria.getEmpleados().size() == 0) // lambda
                .collect(Collectors.toList());
    }

    public Categoria obtenerCategoriaConMinimoSueldo() {
        return this.traerCategorias().stream().min(Comparator.comparing(Categoria::getSueldoBase)).get();
    }

    public List<String> obtenerNombresCategorias() {
        // Mapea cada elemento del stream(colleccion) a otro tipo de elemento
        // en este caso, un String(getNombre)
        return this.traerCategorias().stream().map(cat -> cat.getNombre()).collect(Collectors.toList());
    }
}
