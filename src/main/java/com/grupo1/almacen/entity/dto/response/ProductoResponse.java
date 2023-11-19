package com.grupo1.almacen.entity.dto.response;


import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class ProductoResponse {
    private Long id;
    private String nombre;
    private float costo;
    private String foto;
    private CategoriaResponse categoria;
    private MarcaResponse marca;
    private MedidaResponse medida;
    private TipoAlmacenResponse tipoAlmacen;

}
