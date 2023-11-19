package com.grupo1.almacen.service;


import com.grupo1.almacen.entity.Existencia;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.dto.ExistenciaRequestDTO;
import com.grupo1.almacen.entity.dto.ExistenciaResponseDTO;
import com.grupo1.almacen.entity.dto.MovimientoRequestDTO;
import com.grupo1.almacen.entity.dto.ResultadoResponse;
import com.grupo1.almacen.repository.ExistenciaRepository;
import com.grupo1.almacen.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class ExistenciaService {
    private ExistenciaRepository existenciaRepository;
    private ProductoRepository productoRepository;

    public List<Existencia> listarExistencia() {
        List<Existencia> listarExistencia = existenciaRepository.findAll();
        return listarExistencia;
    }

    public ResultadoResponse crearExistencia(ExistenciaRequestDTO existenciaRequestDTO) {
        String mensaje = "existencia guardada";
        try {
            //Busco el producto segun sus propiedades
            Optional<Producto> productoOptional = productoRepository.findFirstByNombreAndCategoriaNombreAndMarcaNombre(existenciaRequestDTO.getNombre(), existenciaRequestDTO.getCategoria(), existenciaRequestDTO.getMarca());

            if (productoOptional.isPresent()) {
                Producto producto = productoOptional.get();
                //busco también la existencia si el producto se encuentra registrado en uno.
                Optional<Existencia> existenciaOpcional = existenciaRepository.findByProducto(producto);
                if (existenciaOpcional.isPresent()) {
                    Existencia existencia = existenciaOpcional.get();
                    existencia.setCantidad(existencia.getCantidad() + existenciaRequestDTO.getCantidad());
                    existenciaRepository.save(existencia);
                    mensaje = "existencia ya existia, se agrego la cantidad " + existenciaRequestDTO.getCantidad();
                } else {
                    Existencia existenciaNueva = new Existencia();
                    existenciaNueva.setCantidad(existenciaRequestDTO.getCantidad());
                    existenciaNueva.setProducto(producto);
                    existenciaRepository.save(existenciaNueva);
                }
            } else {
                mensaje = "producto no registrado";
            }
        } catch (Exception ex) {
            mensaje = "algo salio mal";
        }

        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }

    public ResultadoResponse aumentarDisminuir(MovimientoRequestDTO movimientoRequestDTO) {
        String mensaje = "aumento satisfactorio";
        try {
            Optional<Existencia> existenciaOptional = existenciaRepository.findById(movimientoRequestDTO.getExistenciaid());
            if (existenciaOptional.isPresent()) {
                Existencia existencia = existenciaOptional.get();
                Producto producto = existencia.getProducto();

                if (producto != null) {
                    if (movimientoRequestDTO.getCantidad() > 0) {
                        Integer cantidadActual = existencia.getCantidad();
                        if (movimientoRequestDTO.getMovimiento() > 0) {
                            Integer cantidadAumentada = cantidadActual + movimientoRequestDTO.getCantidad();
                            existencia.setCantidad(cantidadAumentada);
                        } else {
                            Integer limiteMinimo = producto.getLimiteminimo();
                            Integer cantidadDisponible = cantidadActual - limiteMinimo;
                            if(cantidadDisponible>0){
                                if (movimientoRequestDTO.getCantidad() <= cantidadDisponible) {
                                    cantidadActual = (cantidadDisponible - movimientoRequestDTO.getCantidad()) + limiteMinimo;
                                    existencia.setCantidad(cantidadActual);
                                    mensaje = "se desconto " + movimientoRequestDTO.getCantidad();
                                } else {
                                    mensaje = "no se encuentra  disponible para esa cantidad,solo tenemos disponible "+cantidadDisponible;
                                }
                            }else{
                                mensaje="no hay productos disponibles para descontar,nos encontramos en el límite mínimo";
                                //tal vez pueda registrarse para una alarma futura
                            }
                        }
                        existenciaRepository.save(existencia);
                    } else {
                        mensaje = "debes ingresar un valor mayor  a 0";
                    }
                } else {
                    mensaje = "el producto no existe";
                }
            } else {
                mensaje = "la existencia no existe";
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            mensaje = "Algo salió mal. Detalles: " + ex.getMessage();
        }
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }


}

/*cantidaddisponible =cantidadActual-limite;
        cantidadActual=(cantidaddisponible-loqueingresa)+limite;
        //tambien
        loqueingresa>0 &&loqueingresa>cantidaddisponible*/


