package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="area")
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String descripcion;
    @OneToMany(mappedBy = "area")
    private List<Cargo> listaCargos;
}
