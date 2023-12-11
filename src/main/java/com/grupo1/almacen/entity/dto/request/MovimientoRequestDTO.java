package com.grupo1.almacen.entity.dto.request;

import lombok.Data;

@Data
public class MovimientoRequestDTO {
    private Long existenciaid;
    private Integer movimiento;
    private Integer cantidad;
    private Long pedidoid;
}
