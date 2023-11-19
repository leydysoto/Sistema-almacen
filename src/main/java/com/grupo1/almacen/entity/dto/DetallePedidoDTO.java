package com.grupo1.almacen.entity.dto;

import lombok.Data;

@Data
public class DetallePedidoDTO {
    private Long Id;
    private String nombreProducto;
    private String marca;
    private String img;
    private Integer cantidad;

}
