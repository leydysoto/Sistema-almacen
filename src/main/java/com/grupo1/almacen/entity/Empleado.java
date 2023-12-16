package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="empleado")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombres;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "cargo_id")
    private Cargo cargo;


}
