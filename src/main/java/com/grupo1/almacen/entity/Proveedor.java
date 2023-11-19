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
    private String razonSocial;
    private String distrito;
    private String direccion;
    private String telefono;
    private String Email;
    private String numerodocumento;
    private boolean activo=true;

    @ManyToOne
    @JoinColumn(name="tipoPersona_id")
    private TipoPersona tipopersona;

    @ManyToOne
    @JoinColumn(name="tipo_documento")
    private TipoDocumento tipodocumento;

    //aqui se elimino el mappedBy
    @OneToMany()
    @JoinColumn(name="proveedor_id")
    private List<Producto>productos=new ArrayList<>();

}
