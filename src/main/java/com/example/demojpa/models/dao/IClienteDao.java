package com.example.demojpa.models.dao;

import com.example.demojpa.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
//import org.springframework.transaction.annotation.Transactional;
//import java.util.List;

public interface IClienteDao extends JpaRepository<Cliente,Long> {

    @Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
    public Cliente fetchByIdWithFacturas(Long id);

    public Page<Cliente> findByNombre(String val, Pageable page);

    Page<Cliente> findByNombreContaining(String nombre,Pageable pageable);
    //join fetch funciona como inner join, el left permite que se pueda navegar al detalle
    // del cliente aunque no tenga facturas

    //List<Cliente> findAll();
    //
    //void save(Cliente cliente);
    //
    //Cliente findOne(Long id);
    //
    //void eliminar(Long id);

}
