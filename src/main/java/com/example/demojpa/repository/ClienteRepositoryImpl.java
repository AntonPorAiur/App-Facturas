package com.example.demojpa.repository;

import com.example.demojpa.models.Cliente;
import com.example.demojpa.utils.PageRender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRepositoryImpl {

    @PersistenceContext
    private EntityManager em;

    private CriteriaBuilder cb;

    public ClienteRepositoryImpl(EntityManager em){
        this.em = em;
        this.cb = em.getCriteriaBuilder();
    }

    public Page<Cliente> findByParams(int page, int pageSize, String nombre, String apellido, String email) {

        //CRITERIA
        this.cb = em.getCriteriaBuilder();
        CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
        Root<Cliente> clienteRoot = cq.from(Cliente.class);
        //PREDICATE
        Predicate predicate = getPredicate(nombre, apellido, email, clienteRoot);

        //WHERE CLAUSE
        cq.where(predicate);
        //TYPED QUERY
        TypedQuery<Cliente> query = em.createQuery(cq);
        query.setFirstResult(page * pageSize);
        query.setMaxResults(pageSize);

        //Instancia el Pageable con los parametros page y page size (se puede agregar el sorting)
        Pageable pageable = getPageable(page, pageSize);
        long clientesCuenta = getCuentaCLientes(predicate);
//        System.out.println(query);
//        return query.getResultList();
        return new PageImpl<>(query.getResultList(), pageable, clientesCuenta);
    }

    private Predicate getPredicate(String nombre, String apellido, String email, Root<Cliente> clienteRoot) {

        List<Predicate> predicate = new ArrayList<>();
        if(Objects.nonNull(nombre)){
        //PREDICATES
            predicate.add(
                    cb.like(
                            cb.lower(
                                    clienteRoot.get("nombre")
                            ),"%" + nombre.toLowerCase() + "%"
                    )
            );
        }

        if(Objects.nonNull(apellido)){
            predicate.add(
                    cb.like(
                            cb.lower(
                                    clienteRoot.get("apellido")
                            ),"%" + apellido.toLowerCase() + "%"
                    )
            );
        }

        if(Objects.nonNull(email)){
            predicate.add(
                    cb.like(
                            cb.lower(
                                    clienteRoot.get("email")),
                            "%" + email.toLowerCase() + "%"
                    )
            );
        }

        return cb.and(predicate.toArray(new Predicate[0]));
    }


    private Pageable getPageable(int page, int pageSize){
        return PageRequest.of(page,pageSize);
    }


    private long getCuentaCLientes(Predicate predicate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Cliente> countRoot = countQuery.from(Cliente.class);
        countQuery.select(cb.count(countRoot)).where(predicate);

        return em.createQuery(countQuery).getSingleResult();
    }

}
