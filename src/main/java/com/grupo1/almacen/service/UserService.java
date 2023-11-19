package com.grupo1.almacen.service;


import com.grupo1.almacen.entity.User;

public interface UserService {
    User saveUser(User user);
    void removeSessionMessage();
}
