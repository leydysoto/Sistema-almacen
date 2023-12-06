package com.grupo1.almacen.service;

import com.grupo1.almacen.entity.Tipoalmacen;
import com.grupo1.almacen.entity.dto.response.ResultadoResponse;
import com.grupo1.almacen.repository.TipoalmacenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@AllArgsConstructor
@Service
public class TipoalmacenService {
    private TipoalmacenRepository tipoalmacenRepository;
    public ResultadoResponse crearTipoalmacen(Tipoalmacen tipoalmacen){
        String mensaje="sección de almacen  registrado";
        tipoalmacenRepository.save(tipoalmacen);
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }
}
