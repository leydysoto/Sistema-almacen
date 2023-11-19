package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 128)
    private String nombre;
    private float costo;
    private String foto;
    private Integer limiteminimo;
    @ManyToOne
    @JoinColumn(name="categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name="marca_id")
    private Marca marca;

    @ManyToOne
    @JoinColumn(name="medida_id")
    private Medida medida;

    @ManyToOne
    @JoinColumn(name="tipoalmacen_id")
    private Tipoalmacen tipoalmacen;

    @ManyToOne
    @JoinColumn(name="proveedor_id")
    private Proveedor proveedor;

    @Override
    public String toString() {
        return  nombre +  " - " + marca.getNombre() ;
    }

}
