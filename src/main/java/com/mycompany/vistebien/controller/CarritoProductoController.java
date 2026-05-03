package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.CarritoProductoDAO;
import com.mycompany.vistebien.model.CarritoProducto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarritoProductoController {

    private CarritoProductoDAO dao = new CarritoProductoDAO();

    // Listar y buscar productos en carritos
    @GetMapping("/carrito_producto")
    public String listar(@RequestParam(required = false) String filtro, Model model) {
        if (filtro != null && !filtro.isEmpty()) {
            model.addAttribute("carritoProductos", dao.buscarCarritoProductos(filtro));
        } else {
            model.addAttribute("carritoProductos", dao.listarCarritoProductos());
        }
        return "carrito_producto"; // JSP carrito_producto.jsp
    }

    // Insertar producto en carrito
    @PostMapping("/carrito_producto/insertar")
    public String insertar(@RequestParam int idProducto,
            @RequestParam int idUsuario,
            @RequestParam int cantidad) {
        CarritoProducto cp = new CarritoProducto(idProducto, idUsuario, cantidad);
        dao.insertarCarritoProducto(cp);
        return "redirect:/carrito_producto";
    }

    // Eliminar producto del carrito
    @PostMapping("/carrito_producto/eliminar")
    public String eliminar(@RequestParam String idCarrito,
            @RequestParam String idProducto,
            Model model) {
        try {
            int idCarritoNum = Integer.parseInt(idCarrito);
            int idProductoNum = Integer.parseInt(idProducto);
            dao.eliminarCarritoProducto(idCarritoNum, idProductoNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Los IDs ingresados no son válidos, solo se permiten números.");
        }
        return "redirect:/carrito_producto";
    }

    // Actualizar cantidad de producto en carrito
    @PostMapping("/carrito_producto/actualizar")
    public String actualizar(@RequestParam String idCarrito,
            @RequestParam String idProducto,
            @RequestParam String cantidad,
            Model model) {
        try {
            int idCarritoNum = Integer.parseInt(idCarrito);
            int idProductoNum = Integer.parseInt(idProducto);
            int cantidadNum = Integer.parseInt(cantidad);
            dao.actualizarCarritoProducto(idCarritoNum, idProductoNum, cantidadNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "Los valores ingresados no son válidos, solo se permiten números.");
        }
        return "redirect:/carrito_producto";
    }
}
