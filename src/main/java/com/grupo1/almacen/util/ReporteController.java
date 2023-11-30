package com.grupo1.almacen.util;

import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;

@Controller
public class ReporteController {
    @Autowired
    private ProveedorReporteService proveedorReporteService;



    @GetMapping("report/")
    public String generarReport() throws JRException, FileNotFoundException {
        return  proveedorReporteService.exportarReporte();

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
