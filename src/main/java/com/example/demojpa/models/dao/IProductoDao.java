package com.example.demojpa.models.dao;

import com.example.demojpa.models.Producto;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface IProductoDao extends PagingAndSortingRepository<Producto,Long> {

    @Query("SELECT p FROM Producto p WHERE p.nombre  LIKE %?1%")
    public List<Producto> findbyNombre(String term);

    //La api de permite crear queries con una nomenclatura ya preestablecida, revisar Query Creation
    public List<Producto> findByNombreLikeIgnoreCase(String term);

}
