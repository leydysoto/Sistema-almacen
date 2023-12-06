package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Tipoalmacen;
import com.grupo1.almacen.entity.dto.response.ResultadoResponse;
import com.grupo1.almacen.repository.TipoalmacenRepository;
import com.grupo1.almacen.service.TipoalmacenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@AllArgsConstructor
@Controller
public class TipoalmacenController {
    private TipoalmacenRepository tipoalmacenRepository;
    private TipoalmacenService tipoalmacenService;
    @GetMapping("/tipoalmacen/listar")
    public String tipoalmacen(){

        return "formulario-tipo-almacen";
    }
    @GetMapping("/tipoalmacen/listarTipoalmacen")
    @ResponseBody
    public List<Tipoalmacen> listatipoalmacen(){
        return tipoalmacenRepository.findAll();
    }

    @DeleteMapping("/tipoalmacen/eliminarTipoalmacen/{id}")
    @ResponseBody
    public ResultadoResponse eliminarTipoalmacen(@PathVariable long id ){
        String mensaje="tipo almacén eliminada";
        try {
            tipoalmacenRepository.deleteById(id);

        } catch (Exception e) {
            mensaje="algo salió mal";
        }
        return ResultadoResponse
                .builder()
                .mensaje(mensaje)
                .build();

    }
    @PostMapping("/tipoalmacen/guardar")
    @ResponseBody
    public ResultadoResponse guardarTipoalmacen(@RequestBody Tipoalmacen tipoalmacen){
        return  tipoalmacenService.crearTipoalmacen(tipoalmacen);
    }
}
