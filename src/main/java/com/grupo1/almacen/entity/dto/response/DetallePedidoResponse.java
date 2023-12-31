package com.grupo1.almacen.entity.dto.response;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DetallePedidoResponse {
    private Long id;
    private Integer cantidad;
    private double precio;
    private double total;
    private ProductoResponse producto;
}
