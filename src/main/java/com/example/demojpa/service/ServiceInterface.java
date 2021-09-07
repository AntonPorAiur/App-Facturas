package com.example.demojpa.service;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.Factura;
import com.example.demojpa.models.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface ServiceInterface {

    public List<Cliente> findAll();

//    public Page<Cliente> findAll(Pageable pageable);

    @Transactional(readOnly = true)
    Page<Cliente> findByCriteria(int page, int pageSize, String nombre, String apellido, String email);

    @Transactional(readOnly = true)
    Page<Cliente> findAll(int page, String sortField, String sortDir);

    @Transactional(readOnly = true)
    Page<Cliente> findClienteByNombre(String val, Pageable pageable);

    public void save(Cliente cliente);

    public Cliente findOne(Long id);

    public Cliente fetchbyIdWithFacturas(Long id);

    public void eliminar(Long id);

    public List<Producto> findAllProductos();

    public Page<Producto> findAllProductos(Pageable pageable);

    public List<Producto> findByNombre(String term);

    @Transactional
    void eliminarProducto(Long id);

    public void saveProducto(Producto prodcuto);

    public void saveFactura(Factura factura);

    public Producto findProductoById(Long id);

    public Factura findFacturaById(Long id);

    public Factura fetchFacturaByIdWithClienteWithitemFacturaWithProducto(Long id);

    public void deleteFactura(Long id);



}
