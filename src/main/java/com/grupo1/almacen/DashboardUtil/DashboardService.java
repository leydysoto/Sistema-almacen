package com.grupo1.almacen.DashboardUtil;

import com.grupo1.almacen.entity.Existencia;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.dto.response.*;
import com.grupo1.almacen.repository.ExistenciaRepository;
import com.grupo1.almacen.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class DashboardService {
    private ProductoRepository productoRepository;
    private ExistenciaRepository existenciaRepository;
    public List<ProductoResponse> listaProductos() {
        List<Producto> listaProductos = productoRepository.findAll();

        List<ProductoResponse> productoResponses = new ArrayList<>();
        listaProductos.forEach(producto -> {
            ProductoResponse productoResponse = ProductoResponse.builder()
                    .id(producto.getId())
                    .nombre(producto.getNombre())
                    .costo(producto.getCosto())
                    .foto(producto.getFoto())
                    .categoria(CategoriaResponse.builder()
                            .id(producto.getCategoria().getId())
                            .nombre(producto.getCategoria().getNombre())
                            .build())
                    .marca(MarcaResponse.builder()
                            .id(producto.getMarca().getId())
                            .nombre(producto.getMarca().getNombre())
                            .build())
                    .medida(MedidaResponse.builder()
                            .id(producto.getMedida().getId())
                            .nombre(producto.getMedida().getNombre())
                            .build())
                    .tipoAlmacen(TipoAlmacenResponse.builder()
                            .id(producto.getTipoalmacen().getId())
                            .nombre(producto.getTipoalmacen().getNombre())
                            .build())
                    .build();
            productoResponses.add(productoResponse);
        });
        return productoResponses;
    }

    public List<ExistenciaResponse> listarExistencias() {
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

}
