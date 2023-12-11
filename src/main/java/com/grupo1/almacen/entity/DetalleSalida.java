package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="detallesalidas")
public class DetalleSalida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "salida_id")
    private Salida salida;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
}
