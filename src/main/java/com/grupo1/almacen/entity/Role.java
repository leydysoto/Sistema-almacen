package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
}
