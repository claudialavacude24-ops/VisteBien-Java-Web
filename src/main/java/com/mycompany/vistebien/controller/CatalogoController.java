package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.ProductoDAO;
import com.mycompany.vistebien.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    private ProductoDAO productoDAO = new ProductoDAO();

    // Página general del catálogo
    @GetMapping
    public String catalogoGeneral(Model model) {
        List<Producto> productos = productoDAO.listarProductosConAdministrador();
        model.addAttribute("productos", productos);
        return "catalogo_general"; // JSP con todos los productos
    }

    // Página por categoría
    @GetMapping("/{categoria}")
    public String catalogoPorCategoria(@PathVariable("categoria") String categoria, Model model) {
        List<Producto> productos = productoDAO.listarPorCategoria(categoria);
        model.addAttribute("productos", productos);
        model.addAttribute("categoriaSeleccionada", categoria);
        return "catalogo_categoria"; // JSP dinámico
    }
}
