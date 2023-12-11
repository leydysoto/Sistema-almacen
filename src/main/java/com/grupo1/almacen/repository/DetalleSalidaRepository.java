package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.DetalleSalida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleSalidaRepository extends JpaRepository<DetalleSalida,Long> {
}
