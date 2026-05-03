package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.UsuarioDAO;
import com.mycompany.vistebien.model.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    private UsuarioDAO dao = new UsuarioDAO();

    // Listar y buscar usuarios
    @GetMapping("/usuario")
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute("usuarios", dao.buscarUsuario(filtro));
        } else {
            model.addAttribute("usuarios", dao.listarUsuarios());
        }
        return "usuario"; // JSP usuario.jsp
    }

    // Insertar usuario
    @PostMapping("/usuario/insertar")
    public String insertar(@RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam String telefono,
            @RequestParam String direccion) {

        Usuario u = new Usuario(nombre, correo, contrasena, telefono, direccion);
        dao.insertarUsuario(u);
        return "redirect:/usuario";
    }

    // Actualizar usuario
    @PostMapping("/usuario/actualizar")
    public String actualizar(@RequestParam String id,
            @RequestParam String campo,
            @RequestParam String valor,
            Model model) {
        try {
            int idNum = Integer.parseInt(id);
            dao.actualizarCampo(idNum, campo, valor);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido, solo se permiten números.");
        }
        return "redirect:/usuario";
    }

    // Eliminar usuario
    @PostMapping("/usuario/eliminar")
    public String eliminar(@RequestParam String id, Model model) {
        try {
            int idNum = Integer.parseInt(id);
            dao.eliminarUsuario(idNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido, solo se permiten números.");
        }
        return "redirect:/usuario";
    }
}
