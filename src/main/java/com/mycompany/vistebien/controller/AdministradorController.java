package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.AdministradorDAO;
import com.mycompany.vistebien.model.Administrador;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdministradorController {

    private AdministradorDAO dao = new AdministradorDAO();

    // Listar y buscar administradores
    @GetMapping("/administrador")
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute("administradores", dao.buscarAdministrador(filtro));
        } else {
            model.addAttribute("administradores", dao.listarAdministradores());
        }
        return "administrador"; // JSP administrador.jsp
    }

    // Insertar administrador
    @PostMapping("/administrador/insertar")
    public String insertar(@RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam String telefono) {

        Administrador a = new Administrador(nombre, correo, contrasena, telefono);
        dao.insertarAdministrador(a);
        return "redirect:/administrador";
    }

    // Actualizar administrador
    @PostMapping("/administrador/actualizar")
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
        return "redirect:/administrador";
    }

    // Eliminar administrador
    @PostMapping("/administrador/eliminar")
    public String eliminar(@RequestParam String id, Model model) {
        try {
            int idNum = Integer.parseInt(id);
            dao.eliminarAdministrador(idNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido, solo se permiten números.");
        }
        return "redirect:/administrador";
    }
}
