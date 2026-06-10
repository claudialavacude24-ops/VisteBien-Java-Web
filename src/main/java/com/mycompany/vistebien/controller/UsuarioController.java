package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.UsuarioDAO;
import com.mycompany.vistebien.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private UsuarioDAO dao = new UsuarioDAO();

    // ==============================
    // LISTAR USUARIOS
    // SOLO ADMIN
    // ==============================
    @GetMapping("/usuario")
    public String listar(
            @RequestParam(required = false) String filtro,
            HttpSession session,
            Model model) {

        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("admin")) {
            return "redirect:/login";
        }

        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute(
                    "usuarios",
                    dao.buscarUsuario(filtro));
        } else {
            model.addAttribute(
                    "usuarios",
                    dao.listarUsuarios());
        }

        return "usuario";
    }

    // ==============================
    // INSERTAR USUARIO
    // SOLO ADMIN
    // ==============================
    @PostMapping("/usuario/insertar")
    public String insertar(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam String telefono,
            @RequestParam String direccion,
            HttpSession session) {

        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("admin")) {
            return "redirect:/login";
        }

        Usuario u = new Usuario(
                nombre,
                correo,
                contrasena,
                telefono,
                direccion);

        dao.insertarUsuario(u);

        return "redirect:/usuario";
    }

    // ==============================
    // ACTUALIZAR USUARIO
    // SOLO ADMIN
    // ==============================
    @PostMapping("/usuario/actualizar")
    public String actualizar(
            @RequestParam String id,
            @RequestParam String campo,
            @RequestParam String valor,
            HttpSession session,
            Model model) {

        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("admin")) {
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

        return "redirect:/usuario";
    }

    // ==============================
    // ELIMINAR USUARIO
    // SOLO ADMIN
    // ==============================
    @PostMapping("/usuario/eliminar")
    public String eliminar(
            @RequestParam String id,
            HttpSession session,
            Model model) {

        String rol = (String) session.getAttribute("rol");

        if (rol == null || !rol.equals("admin")) {
            return "redirect:/login";
        }

        try {

            int idNum = Integer.parseInt(id);

            dao.eliminarUsuario(idNum);

        } catch (NumberFormatException e) {

            model.addAttribute(
                    "error",
                    "El ID ingresado no es válido, solo se permiten números.");
        }

        return "redirect:/usuario";
    }
}