package com.example.demojpa.models;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "facturas")
@Getter @Setter
public class Factura implements Serializable {

    public static final Long SerialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String descripcion;

    private String observacion;

    @Column(name = "create_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "factura_id")
    private List<ItemFactura> items;

    public Factura(){
        this.items = new ArrayList<>();
    }

    @PrePersist
    public void prePersist(){
        createAt = new Date();
    }

    public void addItemFactura(ItemFactura item) {
        this.items.add(item);
    }

    public BigDecimal CalculaTotal(){

        BigDecimal total = BigDecimal.ZERO;

        for (ItemFactura item: items){
            BigDecimal subtotal = item.CalcularImporte();
            total = total.add(subtotal);
        }

        return total;
    }

}
