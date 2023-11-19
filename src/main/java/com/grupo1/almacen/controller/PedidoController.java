package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.DetallePedido;
import com.grupo1.almacen.entity.Pedido;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.dto.DetallePedidoDTO;
import com.grupo1.almacen.entity.dto.ResultadoResponse;
import com.grupo1.almacen.entity.dto.response.*;
import com.grupo1.almacen.repository.PedidoRepository;
import com.grupo1.almacen.repository.ProductoRepository;
import com.grupo1.almacen.service.DetallePedidoService;
import com.grupo1.almacen.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Controller
public class PedidoController {
    private ProductoRepository productoRepository;
    private PedidoService pedidoService;
    private DetallePedidoService detallePedidoService;

    @GetMapping("/pedidos/listar")
    public String lista() {
        return "backoffice/pedido/frmPedido";
    }

    @GetMapping("/pedidos/listarProductos")
    @ResponseBody
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

    @PostMapping("/pedidos/guardarPedido")
    @ResponseBody
    public ResultadoResponse guardarPedido(@RequestBody List<DetallePedidoDTO> detallePedidos) {

        String mensaje = "pedido guardado correctamente";
        try {
            Date fechaCreacion = new Date();
            Pedido pedido = new Pedido();
            pedido.setFechaCreacion(fechaCreacion);
            pedido.setNumero(pedidoService.generarNumeroDePedido());
            pedidoService.guardarPedido(pedido);
            for (DetallePedidoDTO detallePedidoDTO : detallePedidos) {

                DetallePedido detallePedido = new DetallePedido();
                Producto producto = new Producto();

                producto.setId(detallePedidoDTO.getId());
                detallePedido.setPedido(pedido);
                detallePedido.setCantidad(detallePedidoDTO.getCantidad());
                detallePedido.setProducto(producto);
                Optional<Producto> productoOptional = productoRepository.findById(detallePedidoDTO.getId());

                if (productoOptional.isPresent()) {
                    Producto p = productoOptional.get();
                    detallePedido.setPrecio(p.getCosto());
                }
                Double total = detallePedido.getCantidad() * detallePedido.getPrecio();
                detallePedido.setTotal(total);
                detallePedidoService.guardarDetallePedido(detallePedido);
            }

        } catch (Exception ex) {
            mensaje = "pedido No registrado";
        }
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();
    }
}
