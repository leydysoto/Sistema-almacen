package com.grupo1.almacen.entity.dto.response;

import lombok.Data;

@Data
public class UsuarioResponse {
    private Integer empleadoid;
    private String nombres;
    private String dni;
    private String direccion;
    private String email;
    private String telefono;
    private String role;
    private String cargo;
}
