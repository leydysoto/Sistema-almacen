package com.grupo1.almacen.entity.dto.response;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class MarcaResponse {
    private Long id;

    private String nombre;
}
