package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.DetallePedido;
import com.grupo1.almacen.repository.DetallePedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class DetallePedidoService {
    private DetallePedidoRepository detallePedidoRepository;

    public DetallePedido guardarDetallePedido(DetallePedido detallePedido){
        return  detallePedidoRepository.save(detallePedido);
    }
}
