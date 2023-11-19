package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    @Column(unique = true)

    private String username;

    private String email;

    private String mobileNo;


    private String password;
    private String role;

    @OneToMany(mappedBy = "usuario")
    private List<Pedido> listaPedidos;
}
