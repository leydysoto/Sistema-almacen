package com.grupo1.almacen.controller;

import com.grupo1.almacen.DashboardUtil.DashboardService;
import com.grupo1.almacen.entity.Categoria;
import com.grupo1.almacen.entity.Marca;
import com.grupo1.almacen.entity.dto.response.ExistenciaResponse;
import com.grupo1.almacen.entity.dto.response.ProductoResponse;
import com.grupo1.almacen.repository.CategoriaRepository;
import com.grupo1.almacen.repository.MarcaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@AllArgsConstructor
@Controller
public class DashboardController {

    private CategoriaRepository categoriaRepository;
    private MarcaRepository marcaRepository;
    private DashboardService dashboardService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }


    @GetMapping("/dashboard/categorias")
    @ResponseBody
    public List<Categoria> categorias() {
        return categoriaRepository.findAll();
    }


    @GetMapping("/dashboard/marcas")
    @ResponseBody
    public List<Marca> marcas() {
        return marcaRepository.findAll();
    }
    @GetMapping("/dashboard/productos")
    @ResponseBody
    public List<ProductoResponse> listaProductos(){
        return dashboardService.listaProductos();
    }
    @GetMapping("/dashboard/existencias")
    @ResponseBody
    public List<ExistenciaResponse> listaExistencias(){
        return dashboardService.listarExistencias();
    }

}
