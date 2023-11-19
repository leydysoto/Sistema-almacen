package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Existencia;
import com.grupo1.almacen.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExistenciaRepository extends JpaRepository<Existencia,Long> {
    Optional<Existencia> findByProducto(Producto producto);
}
