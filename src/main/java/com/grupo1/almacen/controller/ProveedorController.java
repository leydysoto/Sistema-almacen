package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Producto;
import com.grupo1.almacen.entity.Proveedor;
import com.grupo1.almacen.entity.TipoPersona;
import com.grupo1.almacen.repository.ProductoRepository;
import com.grupo1.almacen.repository.ProveedorRepository;
import com.grupo1.almacen.repository.TipoDocumentoRepository;
import com.grupo1.almacen.repository.TipoPersonaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;


@AllArgsConstructor
@Controller
public class ProveedorController {
    private ProductoRepository productoRepository;
    private ProveedorRepository proveedorRepository;
    private TipoDocumentoRepository tipoDocumentoRepository;
    private TipoPersonaRepository tipoPersonaRepository;

    @GetMapping("/proveedores/nuevo")
    public String formularioProveedor(Model model){
        List<Producto> listaProductos=productoRepository.findAll();
        model.addAttribute("proveedor", new Proveedor());
        model.addAttribute("listaProductos",listaProductos);
        model.addAttribute("tipoDocumentos",tipoDocumentoRepository.findAll());
        model.addAttribute("tipoPersonas",tipoPersonaRepository.findAll());
        return "formulario-proveedor";
    }
    @PostMapping("/proveedores/guardar")
    public String guardarProveedor(Proveedor proveedor){
        proveedorRepository.save(proveedor);
        return "redirect:/proveedores/nuevo";
    }

}
