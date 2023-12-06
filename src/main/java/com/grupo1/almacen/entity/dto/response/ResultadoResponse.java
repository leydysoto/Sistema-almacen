package com.grupo1.almacen.entity.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ResultadoResponse {
    private String mensaje;
}
