package com.grupo1.almacen.service;


import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.entity.dto.request.UsuarioRequest;

public interface UserService {
    User guardarUsuario(UsuarioRequest usuarioRequest);
    User guardarCredenciales(User user);
    void removeSessionMessage();
}
