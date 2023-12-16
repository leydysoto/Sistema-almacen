package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area,Integer> {
}
