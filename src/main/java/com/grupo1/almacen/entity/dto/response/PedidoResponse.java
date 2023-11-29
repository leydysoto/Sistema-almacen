package com.grupo1.almacen.entity.dto.response;

import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.entity.enun.Estado;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Builder
@Data
public class PedidoResponse {
    private Long idPedido;
    private String numero;
    private Date fechaCreacion;
    private Date fechaRecibida;
    private String estado;
    private Integer usuario;
    List<DetallePedidoResponse>detallePedidos;
}
