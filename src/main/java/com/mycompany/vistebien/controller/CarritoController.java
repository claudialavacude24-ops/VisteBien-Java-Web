package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.CarritoDAO;
import com.mycompany.vistebien.model.Carrito;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarritoController {

    private CarritoDAO dao = new CarritoDAO();

    // Listar y buscar carritos
    @GetMapping("/carrito")
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute("carritos", dao.buscarCarrito(filtro));
        } else {
            model.addAttribute("carritos", dao.listarCarritos());
        }
        return "carrito"; // JSP carrito.jsp
    }

    // Insertar carrito
    @PostMapping("/carrito/insertar")
    public String insertar(@RequestParam int idUsuario) {
        Carrito c = new Carrito(idUsuario);
        dao.insertarCarrito(c);
        return "redirect:/carrito";
    }

    // Eliminar carrito
    @PostMapping("/carrito/eliminar")
    public String eliminar(@RequestParam String id, Model model) {
        try {
            int idNum = Integer.parseInt(id);
            dao.eliminarCarrito(idNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido, solo se permiten números.");
        }
        return "redirect:/carrito";
    }
}
