package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Tipoalmacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoalmacenRepository extends JpaRepository<Tipoalmacen,Long> {
}