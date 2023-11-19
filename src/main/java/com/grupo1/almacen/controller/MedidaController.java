package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Categoria;
import com.grupo1.almacen.entity.Medida;
import com.grupo1.almacen.entity.dto.ExistenciaRequestDTO;
import com.grupo1.almacen.entity.dto.ResultadoResponse;
import com.grupo1.almacen.repository.MedidaRepository;
import com.grupo1.almacen.service.MedidaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@Controller
public class MedidaController {
    private MedidaService medidaService;
    private MedidaRepository medidaRepository;

    @GetMapping("/medidas/listar")
    public String medidas(){
        return "formulario-medida";
    }
    @GetMapping("/medidas/listarMedidas")
    @ResponseBody
    public List<Medida>listaMedidas(){
        return medidaRepository.findAll();

    }
    @DeleteMapping("/medidas/eliminarMedidas/{id}")
    @ResponseBody
    public ResultadoResponse eliminarMedida(@PathVariable  long id ){
        String mensaje="medida eliminada";
        try {
            medidaRepository.deleteById(id);

        } catch (Exception e) {
            mensaje="algo salió mal";
        }
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }


    @PostMapping("/medidas/guardar")
    @ResponseBody
    public ResultadoResponse guardarMedida(@RequestBody Medida medida ){
        return medidaService.crearMedida(medida);
    }
}
