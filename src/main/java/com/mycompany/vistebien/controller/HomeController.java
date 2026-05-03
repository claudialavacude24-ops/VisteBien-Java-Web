package com.mycompany.vistebien.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    // Página principal con control de rol
    @GetMapping("/index")
    public String index(HttpSession session, Model model) {
        // Recuperar rol de la sesión
        String rol = (String) session.getAttribute("rol");
        if (rol == null) {
            rol = "invitado"; // valor por defecto
        }
        model.addAttribute("rol", rol);
        return "index"; // JSP index.jsp
    }

    // Endpoint para cambiar rol (desde el selector en JSP)
    @PostMapping("/setRol")
    public String setRol(@RequestParam String rol, HttpSession session) {
        session.setAttribute("rol", rol);
        return "redirect:/index";
    }

}
