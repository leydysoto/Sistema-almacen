package com.grupo1.almacen.entity.dto.request;

import lombok.Data;

@Data
public class UsuarioRequest {
    private Integer empleadoid;
    private String nombres;
    private String dni;
    private String direccion;
    private String username;
    private String email;
    private String telefono;
    private String password;
    private Integer roleid;
    private Integer cargoid;
}
