package com.grupo1.almacen.entity;

import com.grupo1.almacen.entity.enun.Estado;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name="pedidos")
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;
    private Estado estado;
    @ManyToOne
    @JoinColumn(name="usuario_id")
    private User usuario;
    @OneToMany(mappedBy="pedido")
    private List<DetallePedido> detallePedidos;
}
