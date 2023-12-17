package com.grupo1.almacen.controller;


import com.grupo1.almacen.entity.Proveedor;

import com.grupo1.almacen.repository.ProductoRepository;
import com.grupo1.almacen.repository.ProveedorRepository;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@AllArgsConstructor
@Controller
public class ProveedorController {
    private ProductoRepository productoRepository;
    private ProveedorRepository proveedorRepository;

    @GetMapping("/proveedores/listar")
    public  String listarProveedor(Model model){
        List<Proveedor> listaProveedor =proveedorRepository.findAll();
        model.addAttribute("listaProveedor",listaProveedor);
        return "proveedor";

    }
    @GetMapping("/proveedores/nuevo")
    public String formularioProveedor(Model model){
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("listaProductos",productoRepository.findAll());
        return "formulario-proveedor";
    }
    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(Proveedor proveedor){
        proveedorRepository.save(proveedor);
        return "redirect:/proveedores/listar";
    }
    @GetMapping("/proveedores/editar/{id}")
    public String mostrarFormularioModificarProveedor(@PathVariable("id") Long id, Model model) {
        Proveedor proveedor=proveedorRepository.findById(id).get();
        model.addAttribute("proveedor",proveedor);
        model.addAttribute("tipoDocumentos",productoRepository.findAll());
        model.addAttribute("listaProductos",productoRepository.findAll());
        return"formulario-proveedor";

    }
    @GetMapping("/proveedores/detalles/{id}")
    public String detalleProveedor(@PathVariable("id") Long id, Model model) {
        Proveedor proveedor=proveedorRepository.findById(id).get();
        model.addAttribute("proveedor",proveedor);
        return"detalle-proveedor";
    }

}
