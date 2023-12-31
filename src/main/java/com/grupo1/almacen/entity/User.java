package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombres;

    @Column(unique = true)
    private String username;

    private String email;

    private String telefono;
    private String password;

    @ManyToOne
    @JoinColumn(name="id_role")
    private Role role;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> listaPedidos;
}
