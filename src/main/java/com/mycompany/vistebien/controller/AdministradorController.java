package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.AdministradorDAO;
import com.mycompany.vistebien.model.Administrador;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdministradorController {

    private AdministradorDAO dao = new AdministradorDAO();

    // =====================================
    // VALIDAR ACCESO ADMIN
    // =====================================
    private boolean esAdmin(HttpSession session) {

        String rol = (String) session.getAttribute("rol");

        return rol != null && rol.equals("admin");
    }

    // =====================================
    // LISTAR Y BUSCAR ADMINISTRADORES
    // SOLO ADMIN
    // =====================================
    @GetMapping("/administrador")
    public String listar(
            @RequestParam(required = false) String filtro,
            HttpSession session,
            Model model) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        if (filtro != null && !filtro.trim().isEmpty()) {

            model.addAttribute(
                    "administradores",
                    dao.buscarAdministrador(filtro));

        } else {

            model.addAttribute(
                    "administradores",
                    dao.listarAdministradores());
        }

        return "administrador";
    }

    // =====================================
    // INSERTAR ADMINISTRADOR
    // SOLO ADMIN
    // =====================================
    @PostMapping("/administrador/insertar")
    public String insertar(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam String telefono,
            HttpSession session) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        Administrador administrador = new Administrador(
                nombre,
                correo,
                contrasena,
                telefono);

        dao.insertarAdministrador(administrador);

        return "redirect:/administrador";
    }

    // =====================================
    // ACTUALIZAR ADMINISTRADOR
    // SOLO ADMIN
    // =====================================
    @PostMapping("/administrador/actualizar")
    public String actualizar(
            @RequestParam String id,
            @RequestParam String campo,
            @RequestParam String valor,
            HttpSession session,
            Model model) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        try {

            int idNum = Integer.parseInt(id);

            dao.actualizarCampo(
                    idNum,
                    campo,
                    valor);

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "error",
                    "El ID ingresado no es válido, solo se permiten números.");
        }

        return "redirect:/administrador";
    }

    // =====================================
    // ELIMINAR ADMINISTRADOR
    // SOLO ADMIN
    // =====================================
    @PostMapping("/administrador/eliminar")
    public String eliminar(
            @RequestParam String id,
            HttpSession session,
            Model model) {

        if (!esAdmin(session)) {
            return "redirect:/login";
        }

        try {

            int idNum = Integer.parseInt(id);

            dao.eliminarAdministrador(idNum);

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "error",
                    "El ID ingresado no es válido, solo se permiten números.");
        }

        return "redirect:/administrador";
    }
}