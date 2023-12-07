package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.*;
import com.grupo1.almacen.entity.dto.response.*;
import com.grupo1.almacen.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    public  Pedido guardarPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public String generarNumeroDePedido(){
        int numero=0;
        String numeroConcatenado="";
        List<Pedido> pedidos=pedidoRepository.findAll();
        List<Integer> numeros=new ArrayList<>();
        pedidos.stream().forEach(ped->numeros.add(Integer.parseInt(ped.getNumero())));

        if (pedidos.isEmpty()){
            numero=1;
        }else{
            numero=numeros.stream().max(Integer::compare).get();
            numero++;
        }

        if(numero<10){
            numeroConcatenado="0000000"+String.valueOf(numero);
        } else if(numero<100){
            numeroConcatenado="000000"+String.valueOf(numero);

        }else if(numero<1000){
            numeroConcatenado="00000"+String.valueOf(numero);
        }

        return numeroConcatenado;
    }

    //crear una lista de los pedidos y sus detalles
    public List<PedidoResponse> listaPedidos(){

        List<Pedido>listaPedidos= pedidoRepository.findAll();
        List<PedidoResponse> pedidoResponse=new ArrayList<>();
        return listaPedidos.stream()
                .map(this::mapPedidoToPedidoResponse)
                .collect(Collectors.toList());

    }
    public PedidoResponse encontrarPedido(Long idPedido) {
        Optional<Pedido> pedidoOptional = pedidoRepository.findById(idPedido);
        if (pedidoOptional.isPresent()) {
            Pedido pedidoEncontrado = pedidoOptional.get();
            PedidoResponse pedidoResponse = mapPedidoToPedidoResponse(pedidoEncontrado);

            return pedidoResponse;
        } else {
            return null;
        }

    }
    private PedidoResponse mapPedidoToPedidoResponse(Pedido pedido) {
        return PedidoResponse.builder()
                .idPedido(pedido.getId())
                .numero(pedido.getNumero())
                .fechaCreacion(pedido.getFechaCreacion())
                .fechaRecibida(pedido.getFechaRecibida())
                .estado(pedido.getEstado() != null ? pedido.getEstado().toString() : null)
                .usuario(pedido.getUsuario()!= null ? pedido.getUsuario().getId() : null)
                .detallePedidos(mapDetallePedidosList(pedido.getDetallePedidos()))
                .build();
    }
    private List<DetallePedidoResponse> mapDetallePedidosList(List<DetallePedido> detallePedidos) {
        return detallePedidos.stream()
                .map(this::mapDetallePedidoToDetallePedidoResponse)
                .collect(Collectors.toList());
    }
    private DetallePedidoResponse mapDetallePedidoToDetallePedidoResponse(DetallePedido detallePedido) {
        return DetallePedidoResponse.builder()
                .id(detallePedido.getId())
                .cantidad(detallePedido.getCantidad())
                .precio(detallePedido.getPrecio())
                .total(detallePedido.getTotal())
                .producto(mapProductoToProductoResponse(detallePedido.getProducto()))
                .build();
    }
    private ProductoResponse mapProductoToProductoResponse(Producto producto) {
        return producto != null ?
                ProductoResponse.builder()
                        .id(producto.getId())
                        .nombre(producto.getNombre())
                        .costo(producto.getCosto())
                        .foto(producto.getFoto())
                        .categoria(mapCategoriaToCategoriaResponse(producto.getCategoria()))
                        .marca(mapMarcaToMarcaResponse(producto.getMarca()))
                        .medida(mapMedidaToMedidaResponse(producto.getMedida()))
                        .tipoAlmacen(mapTipoAlmacenToTipoAlmacenResponse(producto.getTipoalmacen()))
                        .build() :
                null;
    }
    //evitar nullos



    private CategoriaResponse mapCategoriaToCategoriaResponse(Categoria categoria) {
        return categoria != null ?
                CategoriaResponse.builder()
                        .id(categoria.getId())
                        .nombre(categoria.getNombre())
                        .build() :
                null;
    }

    private MarcaResponse mapMarcaToMarcaResponse(Marca marca) {
        return marca != null ?
                MarcaResponse.builder()
                        .id(marca.getId())
                        .nombre(marca.getNombre())
                        .build() :
                null;
    }

    private MedidaResponse mapMedidaToMedidaResponse(Medida medida) {
        return medida != null ?
                MedidaResponse.builder()
                        .id(medida.getId())
                        .nombre(medida.getNombre())
                        .build() :
                null;
    }

    private TipoAlmacenResponse mapTipoAlmacenToTipoAlmacenResponse(Tipoalmacen tipoAlmacen) {
        return tipoAlmacen != null ?
                TipoAlmacenResponse.builder()
                        .id(tipoAlmacen.getId())
                        .nombre(tipoAlmacen.getNombre())
                        .build() :
                null;
    }

}
