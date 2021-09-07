package com.example.demojpa.service;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.models.Factura;
import com.example.demojpa.models.Producto;
import com.example.demojpa.models.dao.IClienteDao;
import com.example.demojpa.models.dao.IFaturaDao;
import com.example.demojpa.models.dao.IProductoDao;
import com.example.demojpa.repository.ClienteRepository;
import com.example.demojpa.utils.PageRender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@Service
public class ServiceImp implements ServiceInterface {

    @Autowired
    private IClienteDao clientedao;

    @Autowired
    private IProductoDao productodao;

    @Autowired
    private IFaturaDao facturadao;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return (List<Cliente>) clientedao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findByCriteria(int page, int pageSize, String nombre, String apellido, String email){
        return clienteRepository.findByParams(page, pageSize, nombre, apellido, email);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findAll(int page, String sortField, String sortDir) {

        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageRequest = PageRequest.of(page,5,sort);
//
//        ExampleMatcher matcher = ExampleMatcher.matchingAll().withMatcher("nombre",contains().ignoreCase());
//        Example<Cliente> example = Example.of(Cliente.from("jorge"),matcher);

//        return clientedao.findAll(example,pageRequest);
        return clientedao.findByNombreContaining("Jorge",pageRequest);
    }


    @Override
    @Transactional(readOnly = true)
    public Page<Cliente> findClienteByNombre(String val,Pageable pageable) {

        return clientedao.findByNombre(val,pageable);
    }

    @Override
    @Transactional
    public void save(Cliente cliente) {
        clientedao.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findOne(Long id) {
        return clientedao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente fetchbyIdWithFacturas(Long id) {
        return clientedao.fetchByIdWithFacturas(id);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        clientedao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findByNombre(String term) {
        return productodao.findByNombreLikeIgnoreCase("%"+term+"%");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Producto> findAllProductos(Pageable pageable) {
        return productodao.findAll(pageable);
    }

    @Override
    @Transactional
    public void saveFactura(Factura factura) {
        facturadao.save(factura);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAllProductos() {
        //El return method requiere el cast a tipo List
        return (List<Producto>) productodao.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findProductoById(Long id) {
        return productodao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void eliminarProducto(Long id) { productodao.deleteById(id); }

    @Override
    @Transactional
    public void saveProducto(Producto producto){ productodao.save(producto);    }

    @Override
    @Transactional(readOnly = true)
    public Factura findFacturaById(Long id) {
        return facturadao.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Factura fetchFacturaByIdWithClienteWithitemFacturaWithProducto(Long id) {
        return facturadao.fetchByIdWithClienteWithItemFacturaWithProducto(id);
    }

    @Override
    @Transactional
    public void deleteFactura(Long id) {
        facturadao.deleteById(id);
    }


}
