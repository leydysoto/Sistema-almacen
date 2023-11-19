package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="detallepedidos")
public class DetallePedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    private double precio;
    private double total;

    @ManyToOne
    @JoinColumn(name="pedido_id")
    private  Pedido pedido;

    @ManyToOne
    @JoinColumn(name="producto_id")
    private Producto producto;
}
