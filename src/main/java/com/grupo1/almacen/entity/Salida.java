package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name="salidas")
public class Salida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    private Date fecha;

    @OneToMany(mappedBy = "salida")
    private List<DetalleSalida> detalles;
}
