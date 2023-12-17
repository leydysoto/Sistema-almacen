package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Movimiento;
import com.grupo1.almacen.repository.MovimientoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@AllArgsConstructor
@Controller
public class MovimientoController {
    private MovimientoRepository movimientoRepository;

    @GetMapping("/user/historial")
    public String historial(Model model){
        List<Movimiento> movimietos = movimientoRepository.listaMovimientos();
        model.addAttribute("movimientos" ,movimietos);
        return "movimientos";

    }
}
