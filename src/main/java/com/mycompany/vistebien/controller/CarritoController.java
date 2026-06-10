package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.CarritoDAO;
import com.mycompany.vistebien.model.Carrito;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarritoController {

    private CarritoDAO dao = new CarritoDAO();

    // =====================================
    // VALIDAR ADMIN
    // =====================================
    private boolean esAdmin(HttpSession session) {

        String rol = (String) session.getAttribute("rol");

        return rol != null && rol.equals("admin");
    }

    // =====================================
    // LISTAR
    // =====================================
    @GetMapping("/carrito")
    public String listar(
            @RequestParam(required = false) String filtro,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        if (filtro != null && !filtro.isEmpty()) {

            model.addAttribute(
                    "carritos",
                    dao.buscarCarrito(filtro));

        } else {

            model.addAttribute(
                    "carritos",
                    dao.listarCarritos());
        }

        return "carrito";
    }

    // =====================================
    // INSERTAR
    // =====================================
    @PostMapping("/carrito/insertar")
    public String insertar(
            @RequestParam int idUsuario,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        Carrito c =
                new Carrito(idUsuario);

        dao.insertarCarrito(c);

        return "redirect:/carrito";
    }

    // =====================================
    // ELIMINAR
    // =====================================
    @PostMapping("/carrito/eliminar")
    public String eliminar(
            @RequestParam String id,
            Model model,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        try {

            int idNum =
                    Integer.parseInt(id);

            dao.eliminarCarrito(idNum);

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "error",
                    "El ID ingresado no es válido.");
        }

        return "redirect:/carrito";
    }
}