package com.grupo1.almacen.entity;

import com.grupo1.almacen.entity.enun.TipoOperacion;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "historial")
public class Historial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "producto_id")
    private Long productoId;

    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacion")
    private TipoOperacion tipoOperacion;

    @Column(name = "fecha")
    private LocalDateTime fecha;

}
