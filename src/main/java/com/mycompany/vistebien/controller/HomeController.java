package com.mycompany.vistebien.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(
            HttpSession session,
            Model model) {

        String rol =
                (String) session.getAttribute("rol");

        if (rol == null) {
            rol = "invitado";
        }

        model.addAttribute(
                "rol",
                rol);

        // Cliente logueado
        model.addAttribute(
                "usuarioLogueado",
                session.getAttribute(
                        "usuarioLogueado"));

        // Administrador logueado
        model.addAttribute(
                "adminLogueado",
                session.getAttribute(
                        "adminLogueado"));

        // Nombre genérico para mostrar
        model.addAttribute(
                "nombreUsuario",
                session.getAttribute(
                        "nombreUsuario"));

        return "index";
    }
}

