package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Categoria;
import com.grupo1.almacen.entity.Existencia;
import com.grupo1.almacen.entity.Marca;
import com.grupo1.almacen.entity.Medida;
import com.grupo1.almacen.entity.dto.*;
import com.grupo1.almacen.repository.*;
import com.grupo1.almacen.service.ExistenciaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
public class ExistenciaController2 {
    private ExistenciaRepository existenciaRepository;

    private ExistenciaService existenciaService;
    private CategoriaRepository categoriaRepository;
    private MarcaRepository marcaRepository;
    private MedidaRepository medidaRepository;
    private ProductoRepository productoRepository;


    @GetMapping("/existencias/listar")
    public String muestraexistencias(Model model){
        model.addAttribute("listaExistencias",existenciaRepository.findAll());
        return "backoffice/existencia/frmexistencia";
    }

    @GetMapping("/existencias/listarExistencias")
    @ResponseBody
    public List<Existencia>listaExistencia(){

        return existenciaService.listarExistencia();

    }


    @GetMapping("/existencias/categoria")
    @ResponseBody
    public List<Categoria>categoria(){

        return categoriaRepository.findAll();
    }
    @GetMapping("/existencias/marca")
    @ResponseBody
    public List<Marca>marca(){

        return marcaRepository.findAll();

    }
    @GetMapping("/existencias/medida")
    @ResponseBody
    public List<Medida>medida(){
        return medidaRepository.findAll();

    }
    @GetMapping("/existencias/nombresproducto")
    @ResponseBody
    public List<Map<String, String>> nombresproductos() {
        List<NombreProductoProjection> productNames = productoRepository.findUniqueProductNames();
        List<Map<String, String>> result = new ArrayList<>();
        for (NombreProductoProjection productName : productNames) {
            Map<String, String> item = new HashMap<>();
            item.put("nombre", productName.getNombre());
            result.add(item);
        }
        return result;
    }

    @PostMapping("/existencias/guardar")
    @ResponseBody
    public ResultadoResponse guardarCategoria(@RequestBody ExistenciaRequestDTO existenciaRequestDTO){
        return existenciaService.crearExistencia(existenciaRequestDTO);
    }
    @PostMapping("/existencias/aumentardisminuir")
    @ResponseBody
    public ResultadoResponse aumentarDisminuirExistencia(@RequestBody  MovimientoRequestDTO movimientoRequestDTO){
        return existenciaService.aumentarDisminuir(movimientoRequestDTO);
    }


}
