package com.grupo1.almacen.entity.dto.response;

import com.grupo1.almacen.entity.Pedido;
import com.grupo1.almacen.entity.Producto;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
