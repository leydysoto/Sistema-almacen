package com.grupo1.almacen.controller;

import com.grupo1.almacen.configure.CustomUser;
import com.grupo1.almacen.entity.DetallePedido;
import com.grupo1.almacen.entity.Pedido;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.entity.dto.request.DetallePedidoDTO;
import com.grupo1.almacen.entity.dto.response.ResultadoResponse;
import com.grupo1.almacen.entity.dto.response.*;
import com.grupo1.almacen.entity.enun.Estado;
import com.grupo1.almacen.repository.ProductoRepository;
import com.grupo1.almacen.service.DetallePedidoService;
import com.grupo1.almacen.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    //exhibir productos para realizar pedidos
    @GetMapping("/pedidos")
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


    //vizualizar los pedidos y sus detalles   solicitados
    @GetMapping("/pedidos/listar")
    public String muestralistaPedidos() {
        return "backoffice/pedido/pedido";
    }
    @GetMapping("/pedidos/listarPedidos")
    @ResponseBody
    public List<PedidoResponse>listaPedidos(){
        return pedidoService.listaPedidos();
    }

    @PostMapping("/pedidos/guardarPedido")
    @ResponseBody
    public ResultadoResponse guardarPedido(@RequestBody List<DetallePedidoDTO> detallePedidos) {

        String mensaje = "pedido guardado correctamente";
        try {
            Date fechaCreacion = new Date();
            Pedido pedido = new Pedido();
            pedido.setNumero(pedidoService.generarNumeroDePedido());
            pedido.setFechaCreacion(fechaCreacion);
            //falta ->fechaRecibida;
            pedido.setEstado(Estado.PENDIENTE);
            pedidoService.guardarPedido(pedido);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUser customUser = (CustomUser) authentication.getPrincipal();
            User user=customUser.getUser();

            pedido.setUsuario(user);


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
    @PatchMapping("/pedidos/{id}/actualizarEstado")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void actualizarEstadoPedido(
            @PathVariable Long id,
            @RequestParam String nuevoEstado) {
        try{
            pedidoService.actualizarEstadoPedido(id, nuevoEstado);
        }catch(Exception e){
            e.printStackTrace();

        }
    }
}

