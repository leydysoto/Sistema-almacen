package com.grupo1.almacen.entity.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExistenciaResponse {
    private Long id;
    private Integer cantidad;

    private ProductoResponse producto;
}
