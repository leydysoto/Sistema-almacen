package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Salida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalidaRepository extends JpaRepository<Salida,Long> {
    //tenia un problema usando este metodo ya que indicaba que podria devolver mas de uno
    //Optional<Salida> findByPedidoId(Long pedidoid);
    @Query("SELECT s FROM Salida s WHERE s.pedido.id = :pedidoId")
    Optional<Salida> findFirstByPedidoId(@Param("pedidoId") Long pedidoId);
}
