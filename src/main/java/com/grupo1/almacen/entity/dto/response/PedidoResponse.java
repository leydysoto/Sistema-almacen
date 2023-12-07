package com.grupo1.almacen.entity.dto.response;

import lombok.Builder;
import lombok.Data;


import java.util.Date;
import java.util.List;

@Builder
@Data
public class PedidoResponse {
    private Long idPedido;
    private String numero;
    private String fechaCreacion;
    private String fechaRecibida;
    private String estado;
    private Integer usuario;
    List<DetallePedidoResponse>detallePedidos;
}
