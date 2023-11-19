package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Medida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidaRepository extends JpaRepository<Medida,Long> {
}
