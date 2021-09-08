package ar.com.ada.api.empleados.services;

import java.util.*;
import java.util.Optional;

import org.hibernate.sql.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.empleados.entities.Categoria;
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

}
