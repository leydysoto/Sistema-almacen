package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovimientoRepository extends JpaRepository<Movimiento,Integer> {
    @Query(value="{call listaMovimientos()}",nativeQuery = true)
    List<Movimiento> listaMovimientos();
}
