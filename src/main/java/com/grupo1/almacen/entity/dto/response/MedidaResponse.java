package com.grupo1.almacen.entity.dto.response;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class MedidaResponse {
    private Long id;
    private String nombre;
}
