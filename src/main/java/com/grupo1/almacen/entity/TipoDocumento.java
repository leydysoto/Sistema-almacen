package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="tipo_documento")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    //dni-carnet de extranjeria-ruc

}
