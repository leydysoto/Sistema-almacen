package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo,Integer> {
}
