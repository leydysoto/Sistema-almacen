package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.TipoPersona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoPersonaRepository extends JpaRepository<TipoPersona,Integer> {
}
