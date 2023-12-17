package com.grupo1.almacen.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="proveedor")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String razon_social;
    private String distrito;
    private String direccion;
    private String telefono;
    private String email;
    private String tipoproveedor;
    private String numerodocumento;
    private boolean activo=true;


    //aqui se elimino el mappedBy
    @OneToMany()
    @JoinColumn(name="proveedor_id")
    private List<Producto>productos=new ArrayList<>();


}
