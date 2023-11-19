package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /*User findByEmail(String email);*/
    User findByUsername(String username);

}
