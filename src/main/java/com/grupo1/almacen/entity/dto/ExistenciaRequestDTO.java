package com.grupo1.almacen.entity.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ExistenciaRequestDTO {
    private Long idexistencia;
    private String nombre;
    private String categoria;
    private String marca;
    private String medida;
    private String tipoalmacen;
    private Integer  cantidad;
}
