package com.grupo1.almacen.util;

import com.grupo1.almacen.entity.Proveedor;
import com.grupo1.almacen.repository.ProveedorRepository;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.FileNotFoundException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
public class ReporteController {
    @Autowired
    private ProveedorReporteService proveedorReporteService;
    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping("/reporte/")
    public String generarReport(Model model) throws JRException, FileNotFoundException {
        String path = proveedorReporteService.exportarReporte();
        String mensaje = (path != null && !path.isEmpty()) ? "¡El reporte se generó y descargó exitosamente!" : "¡Error al generar el reporte!";
        return "redirect:/reporte/proveedor?mensaje=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8);

    }
    @GetMapping("/reporte/proveedor")
    public String mostrarProveedor(@RequestParam(name = "mensaje", required = false) String mensaje, Model model) {
        List<Proveedor> listaProveedor = proveedorRepository.findAll();
        model.addAttribute("listaProveedor", listaProveedor);
        model.addAttribute("mensaje", mensaje);
        return "proveedor";
    }



    //videos para orientarme en la generacion de reportes
    //https://www.youtube.com/watch?v=YnZZm-P7lPs&t=1154s
    //la maldita programadora bytes
    //https://www.youtube.com/watch?v=E2zosLVTAPk&t=293s
    //Techi de quien me guie mas pero cambie unas cositas
    //https://www.youtube.com/watch?v=pc4lfKm8NLY&t=909s
    //un programador nace - tiene tip  pero lo hace en memoria
    //https://www.youtube.com/watch?v=UExwNGhEcZc&t=4491s
    //diseño
    //https://www.youtube.com/watch?v=MiG-sAbupos


}
