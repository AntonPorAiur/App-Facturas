package com.example.demojpa.repository;

import com.example.demojpa.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente,Long> {

    Page<Cliente> findByParams(int page, int pageSize, String nombre, String apellido, String email);
}
