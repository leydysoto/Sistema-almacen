package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.Empleado;
import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.entity.dto.request.UsuarioRequest;
import com.grupo1.almacen.entity.dto.response.UsuarioResponse;
import com.grupo1.almacen.repository.CargoRepository;
import com.grupo1.almacen.repository.EmpleadoRepository;
import com.grupo1.almacen.repository.RoleRepository;
import com.grupo1.almacen.repository.UserRepository;
import com.grupo1.almacen.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Controller
public class HomeController {

    private UserService userService;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private CargoRepository cargoRepository;
    private EmpleadoRepository empleadoRepository;

    @ModelAttribute
    public void commonUser(Principal p,Model model){
        if(p!=null){
            String username  =p.getName();
            User user = userRepository.findByUsername(username);
            model.addAttribute("user",user);
        }
    }
    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @GetMapping("/user/profile")
    public String profile(Principal p, Model model){
        String username  =p.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user",user);
        return "profile";
    }

    @GetMapping("/registroEmpleado")
    public String registrarEmpleado( Model model){
        model.addAttribute("usuarioRequest", new UsuarioRequest());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());
        return "formulario-empleado";
    }

    @PostMapping("/guardarUsuario")
    public String guardarUsuario(@ModelAttribute UsuarioRequest usuariorequest, HttpSession session ){
        try {
            User usuarioGuardado = userService.guardarUsuario(usuariorequest);
            if (usuarioGuardado != null) {
                session.setAttribute("msg", "registro satisfactorio");
            } else {
                session.setAttribute("msg", "hubo un error");
            }
            return "redirect:/empleados/listar";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/registroEmpleado";
        }
    }
    @GetMapping("/empleados/editar/{id}")
    public String editarEmpleado(@PathVariable("id")Integer id, Model model){
        Empleado empleado= empleadoRepository.findById(id).get();

        UsuarioRequest usuarioRequest= new UsuarioRequest();
        usuarioRequest.setEmpleadoid(empleado.getId());
        usuarioRequest.setNombres(empleado.getNombres());
        usuarioRequest.setDni(empleado.getDni());
        usuarioRequest.setDireccion(empleado.getDireccion());
        usuarioRequest.setUsername(empleado.getUser().getUsername());
        usuarioRequest.setEmail(empleado.getEmail());
        usuarioRequest.setTelefono(empleado.getTelefono());
        usuarioRequest.setRoleid(empleado.getUser().getRole().getId());
        usuarioRequest.setCargoid(empleado.getCargo().getId());

        model.addAttribute("usuarioRequest", usuarioRequest);
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("cargos", cargoRepository.findAll());

        return "formulario-empleado";
    }
    @GetMapping("/empleados/listar")
    public String mostrarEmpleados(Model model){
        List<Empleado> listaEmpleados=empleadoRepository.findAll();
        List<UsuarioResponse> usuarioResponses=new ArrayList<>();

        for(Empleado empleado:listaEmpleados ){
            UsuarioResponse usuarioResponse= new UsuarioResponse();

            usuarioResponse.setEmpleadoid(empleado.getId());
            usuarioResponse.setNombres(empleado.getNombres());
            usuarioResponse.setDni(empleado.getDni());
            usuarioResponse.setDireccion(empleado.getDireccion());
            usuarioResponse.setEmail(empleado.getEmail());
            usuarioResponse.setTelefono(empleado.getTelefono());
            usuarioResponse.setRole(empleado.getUser().getRole().getName());
            usuarioResponse.setCargo(empleado.getCargo().getName());
            usuarioResponses.add(usuarioResponse);

        }
        model.addAttribute("listaEmpleados",usuarioResponses);

        return "empleado";
    }
    @GetMapping("/empleados/credenciales/modificar/{empleadoid}")
    public  String modificarCredenciales(@PathVariable("empleadoid")Integer empleadoid, Model model){

        Empleado empleado= empleadoRepository.findById(empleadoid).get();
        User user=empleado.getUser();
        model.addAttribute("user",user);
        return "credenciales";

    }
    @PostMapping("/empleados/credenciales/guardar")
    public  String guardarCredenciales( @ModelAttribute User user){

        User userGuardado=userService.guardarCredenciales(user);

        if(userGuardado!=null){
            return "redirect:/empleados/listar";
        }else{
            return "redirect:/empleados/credenciales/modificar/"+ user.getId();
        }

    }









}
