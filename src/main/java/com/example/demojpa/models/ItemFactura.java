package com.example.demojpa.models;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter @Setter
@Entity
@Table(name = "facturas_items")
public class ItemFactura implements Serializable {

    private static final Long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer cantidad;

    @ManyToOne(fetch = FetchType.LAZY)
    private Producto producto;

    public BigDecimal CalcularImporte(){
        return producto.getPrecio().multiply(new BigDecimal(cantidad));
    }
}