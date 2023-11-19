package com.grupo1.almacen.entity.dto;

import lombok.Data;

@Data
public class MovimientoRequestDTO {
    private Long existenciaid;
    private Integer movimiento;
    private Integer cantidad;
}
