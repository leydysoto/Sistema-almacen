package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Marca;
import com.grupo1.almacen.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;


    @GetMapping("/marcas/listar")
    public String marcas(Model model) {
        List<Marca> listaCategorias = marcaRepository.findAll();
        model.addAttribute("listaMarcas", listaCategorias);
        return "marca";
    }

    @GetMapping("/marcas/nuevo")
    public String mostrarFormularioNuevaMarca(Model modelo) {
        modelo.addAttribute("marca", new Marca());

        return "formulario-marca";
    }

    @PostMapping("/marcas/guardar")
    public String guardarCategoria(Marca marca) {
        marcaRepository.save(marca);
        return "redirect:/marcas/listar";
    }
}