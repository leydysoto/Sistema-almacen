package com.grupo1.almacen.service;


import com.grupo1.almacen.entity.Existencia;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.dto.request.ExistenciaRequestDTO;
import com.grupo1.almacen.entity.dto.request.MovimientoRequestDTO;
import com.grupo1.almacen.entity.dto.response.*;
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
    private SalidaService salidaService;

    public List<ExistenciaResponse> listarExistencia() {
        List<Existencia> Existencias = existenciaRepository.findAll();
        List<ExistenciaResponse>listarExistencia=new ArrayList<>();
        Existencias.forEach( existencia->{
            ExistenciaResponse existenciaResponse=ExistenciaResponse.builder()
                    .id(existencia.getId())
                    .cantidad(existencia.getCantidad())
                    .producto(ProductoResponse.builder()
                            .id(existencia.getProducto().getId())
                            .nombre(existencia.getProducto().getNombre())
                            .costo(existencia.getProducto().getCosto())
                            .foto(existencia.getProducto().getFoto())
                            .categoria(CategoriaResponse.builder()
                                    .id(existencia.getProducto().getCategoria().getId())
                                    .nombre(existencia.getProducto().getCategoria().getNombre())
                                    .build())
                            .marca(MarcaResponse.builder()
                                    .id(existencia.getProducto().getMarca().getId())
                                    .nombre(existencia.getProducto().getMarca().getNombre())
                                    .build())
                            .medida(MedidaResponse.builder()
                                    .id(existencia.getProducto().getMedida().getId())
                                    .nombre(existencia.getProducto().getMedida().getNombre())
                                    .build())
                            .tipoAlmacen(TipoAlmacenResponse.builder()
                                    .id(existencia.getProducto().getTipoalmacen().getId())
                                    .nombre(existencia.getProducto().getTipoalmacen().getNombre())
                                    .build())
                            .build()

                    ).build();
                    listarExistencia.add(existenciaResponse);

        });

        return listarExistencia;
    }

    public ResultadoResponse crearExistencia(ExistenciaRequestDTO existenciaRequestDTO) {
        String mensaje = "existencia guardada";
        try {
            //Busco el producto segun sus propiedades
            Optional<Producto> productoOptional = productoRepository.findFirstByNombreAndCategoriaNombreAndMarcaNombre(existenciaRequestDTO.getNombre(), existenciaRequestDTO.getCategoria(), existenciaRequestDTO.getMarca());

            if (productoOptional.isPresent()) {//producto existe  en la tabla producto ahora puedes agregarle a existencias
                Producto producto = productoOptional.get();
                //busco también la existencia, si el producto se encuentra registrado en uno.
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
        Integer salida=null;
        try {
            Optional<Existencia> existenciaOptional = existenciaRepository.findById(movimientoRequestDTO.getExistenciaid());
            if (existenciaOptional.isPresent()) {
                Existencia existencia = existenciaOptional.get();
                Producto producto = existencia.getProducto();

                if (producto != null) {
                    //si se ingresa una cantidad  que sea mayor a cero
                    if (movimientoRequestDTO.getCantidad() > 0) {
                        Integer cantidadActual = existencia.getCantidad();
                        //movimiento>=1;aumentará  la cantidad de la existencia
                        if (movimientoRequestDTO.getMovimiento() > 0) {
                            Integer cantidadAumentada = cantidadActual + movimientoRequestDTO.getCantidad();
                            existencia.setCantidad(cantidadAumentada);
                        } else {//movimiento<=0;disminuirá la cantidad de la existencia
                            Integer limiteMinimo = producto.getLimiteminimo();
                            Integer cantidadDisponible = cantidadActual - limiteMinimo;
                            if(cantidadDisponible>0){
                                if (movimientoRequestDTO.getCantidad() <= cantidadDisponible) {
                                    cantidadActual = (cantidadDisponible - movimientoRequestDTO.getCantidad()) + limiteMinimo;
                                    existencia.setCantidad(cantidadActual);
                                    mensaje = "se desconto " + movimientoRequestDTO.getCantidad();

                                    ////////////////se crea la salida
                                    salida=salidaService.crearDetalleSalida(movimientoRequestDTO.getPedidoid(),producto,movimientoRequestDTO.getCantidad());

                                } else {
                                    mensaje = "no se encuentra  disponible para esa cantidad,solo tenemos disponible "+cantidadDisponible;
                                    //podria hacer que descuente lo suficiente pero prefiero que la persona lo haga.
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
            System.out.println(salida);
        }
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }


}

/*cantidaddisponible =cantidadActual-limite;
        cantidadActual=(cantidaddisponible-loqueRetira)+limite;
        //tambien
        loqueRetira>0 &&loqueRetira>cantidaddisponible*/



