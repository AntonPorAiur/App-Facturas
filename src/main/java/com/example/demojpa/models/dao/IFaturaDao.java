package com.example.demojpa.models.dao;

import com.example.demojpa.models.Factura;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface IFaturaDao extends CrudRepository<Factura,Long> {

    @Query("select f from Factura f join fetch f.cliente c join  fetch f.items l join fetch l.producto where f.id=?1")
    public Factura fetchByIdWithClienteWithItemFacturaWithProducto(Long id);

}
