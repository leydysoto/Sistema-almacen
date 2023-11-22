package com.grupo1.almacen.controller;

import com.grupo1.almacen.entity.User;
import com.grupo1.almacen.repository.UserRepository;
import com.grupo1.almacen.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @ModelAttribute
    public void commonUser(Principal p,Model model){
        if(p!=null){
            String username  =p.getName();
            User user = userRepository.findByUsername(username);
            model.addAttribute("user",user);
        }
    }
    //prueba
    @GetMapping("/entrada/index")
    public String bienvenida(Authentication auth, HttpSession session) {
        String username=auth.getName();
        if(session.getAttribute("user")==null){
            User usu = userRepository.findByUsername(username);
            usu.setPassword(null);
            session.setAttribute("usu",usu);
        }
        return "bienvenida";
    }
    //fin de prueba


    @GetMapping("/register")
    public String register( Model model)
    {
        model.addAttribute("user",new User());
        return "register";
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

    @PostMapping("/saveUser")
    public String saveUser( @ModelAttribute User user, HttpSession session ){
        try {

            User us = userService.saveUser(user);
            if (us != null) {
                session.setAttribute("msg", "registro satisfactorio");
            } else {
                session.setAttribute("msg", "hubo un error");
            }
            return "redirect:/register";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/register";
        }
    }




}
