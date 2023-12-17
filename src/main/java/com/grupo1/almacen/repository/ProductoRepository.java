package com.grupo1.almacen.repository;

import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.dto.request.NombreProductoProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto,Long> {
    @Query("SELECT DISTINCT p.nombre as nombre FROM Producto p")
    List<NombreProductoProjection> findUniqueProductNames();

    Optional<Producto> findFirstByNombreAndCategoriaNombreAndMarcaNombreAndMedidaNombre(String nombreProducto, String nombreCategoria, String nombreMarca,String nombreMedida);
    List<Producto> findByNombre(String producto);


    //filtrar por categoria
   /* @Query("SELECT a FROM Producto a WHERE a.categoria.nombre = :categoria")
    public abstract List<Producto> listaProductosPorCategoria(@Param("categoria") String nombreCategoria);*/

    //filtrar por nombre de producto
   /* @Query("SELECT p FROM Producto p WHERE p.nombre LIKE %:nombre%")
    public List<Producto> findByNombreLike(@Param("nombre") String nombre);*/



}