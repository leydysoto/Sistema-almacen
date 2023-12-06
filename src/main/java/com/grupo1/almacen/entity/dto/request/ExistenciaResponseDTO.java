package com.grupo1.almacen.entity.dto.request;

import lombok.Data;

@Data
public class ExistenciaResponseDTO {
    private Long id;
    private Long idProducto;

    private String nombreProducto;
    private String categoria;
    private String marca;
    private Integer  cantidad;
}
