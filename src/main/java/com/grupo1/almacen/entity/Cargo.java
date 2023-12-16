package com.grupo1.almacen.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="cargo")
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToOne
    @JoinColumn(name="Area_id")
    private Area area;

}
