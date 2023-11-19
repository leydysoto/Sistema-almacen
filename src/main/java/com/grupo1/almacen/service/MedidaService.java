package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.Medida;
import com.grupo1.almacen.entity.dto.ResultadoResponse;
import com.grupo1.almacen.repository.MedidaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class MedidaService {
    private MedidaRepository medidaRepository;
    public ResultadoResponse crearMedida(Medida medida){
        String mensaje="medida registrada";
        medidaRepository.save(medida);
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();
    }
}
