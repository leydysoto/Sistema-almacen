package com.grupo1.almacen.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Movimiento {
    @Id
    private Integer historialid;
    private Integer cantidad;
    private String tipooperacion;
    private LocalDateTime fecha;
    private String nombreproducto;
    private String foto;
}
