package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.DetalleSalida;
import com.grupo1.almacen.entity.Pedido;
import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.Salida;
import com.grupo1.almacen.repository.DetalleSalidaRepository;
import com.grupo1.almacen.repository.PedidoRepository;
import com.grupo1.almacen.repository.SalidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@AllArgsConstructor
@Service
public class SalidaService {
    private SalidaRepository salidaRepository;
    private PedidoRepository pedidoRepository;
    private DetalleSalidaRepository detalleSalidaRepository;

    public Integer crearDetalleSalida(Long idPedido, Producto producto,Integer cantidad){
        Integer resultado=1;
        DetalleSalida nuevoDetalle = new DetalleSalida();
        nuevoDetalle.setCantidad(cantidad);
        nuevoDetalle.setProducto(producto);
        //encontrar salida mediante id del pedido
        Optional<Salida> salidaExistente = salidaRepository.findById(idPedido);
        if (salidaExistente.isPresent()) {
            Salida salidaEncontrada = salidaExistente.get();
            nuevoDetalle.setSalida(salidaEncontrada);
        }else{
            //crear una salida
            Salida nuevaSalida=new Salida();
            Date fecha=new Date();

            Pedido pedido=pedidoRepository.findById(idPedido).get();
            pedido.setFechaRecibida(fecha);
            pedidoRepository.save(pedido);

            nuevaSalida.setPedido(pedido);
            nuevaSalida.setFecha(fecha);
            Salida SalidaGuardada=salidaRepository.save(nuevaSalida);
            nuevoDetalle.setSalida(SalidaGuardada);

            resultado=0;

        }
        detalleSalidaRepository.save(nuevoDetalle);

        return resultado;

    }
}
