package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Categoria;
import com.grupo1.almacen.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias/listar")
    public String categorias(Model model){
        //taer la lista de categorias para que se visualice en esta pagina
        List<Categoria> listaCategorias=categoriaRepository.findAll();
        model.addAttribute("listaCategorias",listaCategorias);
        return "categoria";
    }
    @GetMapping("/categorias/nuevo")
    public String mostrarFormularioNuevaCategoria(Model modelo){
        modelo.addAttribute("categoria",new Categoria());
        return"formulario-categoria";
    }
    @PostMapping("/categorias/guardar")
    public String guardarCategoria(Categoria categoria){
        //lo guardo en el repositorio de categorias
        categoriaRepository.save(categoria);
        return "redirect:/categorias/listar";
    }
}
