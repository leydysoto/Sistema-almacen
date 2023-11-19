package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.*;
import com.grupo1.almacen.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
@AllArgsConstructor
@Controller
public class ProductoController {

    private ProductoRepository productoRepository;

    private CategoriaRepository categoryRepository;
    private MarcaRepository marcaRepository;
    private MedidaRepository medidaRepository;
    private TipoalmacenRepository tipoalmacenRepository;

    @GetMapping("/productos/listar")
    public String muestra(Model model) {
        List<Producto> listaProductos = productoRepository.findAll();
        model.addAttribute("listaProductos", listaProductos);
        return "producto";
    }

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        List<Categoria> listaCategorias = categoryRepository.findAll();
        List<Marca> listaMarcas = marcaRepository.findAll();
        List<Medida> listaMedidas = medidaRepository.findAll();
        List<Tipoalmacen> listaTiposAlmacen = tipoalmacenRepository.findAll();
        model.addAttribute("producto", new Producto());
        model.addAttribute("listaCategorias", listaCategorias);
        model.addAttribute("listaMarcas", listaMarcas);
        model.addAttribute("listaMedidas", listaMedidas);
        model.addAttribute("listaTiposAlmacen", listaTiposAlmacen);
        return "formulario-producto";
    }

    @PostMapping("/productos/guardar")
    //para evitar el nullpointerexecption
    public String guardarProducto(@RequestParam (name="file",required = false) MultipartFile foto, Producto producto,
                                  RedirectAttributes flash) {
        if (!foto.isEmpty()) {
            //vamos a guardar las fotos en un directorio fuera del proyecto
            String ruta = "C://Temp//uploads";
            try {
                byte[] bytes = foto.getBytes();
                Path rutaAbsoluta = Paths.get(ruta + "//" + foto.getOriginalFilename());
                Files.write(rutaAbsoluta, bytes);

                producto.setFoto(foto.getOriginalFilename());


            } catch (Exception e) {

            }
            productoRepository.save(producto);
            flash.addFlashAttribute("success","foto subida correctamente");
        }

        return "redirect:/productos/listar";

    }

    @GetMapping("/productos/editar/{id}")
    public String mostrarFormularioModificarProducto(@PathVariable("id") Long id, Model model) {
        Producto producto = productoRepository.findById(id).get();
        model.addAttribute("producto", producto);

        List<Categoria> listaCategorias = categoryRepository.findAll();
        List<Marca> listaMarcas = marcaRepository.findAll();
        List<Medida> listaMedidas = medidaRepository.findAll();
        List<Tipoalmacen> listaTiposAlmacen = tipoalmacenRepository.findAll();

        model.addAttribute("listaCategorias", listaCategorias);
        model.addAttribute("listaMarcas", listaMarcas);
        model.addAttribute("listaMedidas", listaMedidas);
        model.addAttribute("listaTiposAlmacen", listaTiposAlmacen);

        return "formulario-producto";
    }

}