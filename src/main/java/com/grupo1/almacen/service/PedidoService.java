package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.Pedido;
import com.grupo1.almacen.repository.PedidoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;
    public  Pedido guardarPedido(Pedido pedido){
        return pedidoRepository.save(pedido);
    }
    public List<Pedido> listaPedidos(){
        return pedidoRepository.findAll();
    }
    public String generarNumeroDePedido(){
        int numero=0;
        String numeroConcatenado="";
        List<Pedido> pedidos=listaPedidos();
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

}
