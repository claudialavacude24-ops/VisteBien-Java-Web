package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.CarritoDAO;
import com.mycompany.vistebien.dao.CarritoProductoDAO;
import com.mycompany.vistebien.dao.CompraDAO;
import com.mycompany.vistebien.dao.DetalleCompraDAO;
import com.mycompany.vistebien.dao.MetodoPagoDAO;
import com.mycompany.vistebien.dao.ProductoDAO;

import com.mycompany.vistebien.model.Carrito;
import com.mycompany.vistebien.model.Compra;
import com.mycompany.vistebien.model.ItemCarrito;
import com.mycompany.vistebien.model.MetodoPago;
import com.mycompany.vistebien.model.Usuario;

import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagoController {

    private CarritoProductoDAO cpDAO
            = new CarritoProductoDAO();

    private MetodoPagoDAO metodoPagoDAO
            = new MetodoPagoDAO();

    @GetMapping("/pago")
    public String mostrarPago(
            HttpSession session,
            Model model) {

        Usuario usuario
                = (Usuario) session.getAttribute(
                        "usuarioLogueado");

        if (usuario == null) {
            return "redirect:/login";
        }

        List<ItemCarrito> items
                = cpDAO.obtenerItemsCarrito(
                        usuario.getIdUsuario());

        double total = 0;

        for (ItemCarrito item : items) {

            total += item.getSubtotal();
        }

        List<MetodoPago> metodosPago
                = metodoPagoDAO.listarMetodosPago();

        model.addAttribute(
                "items",
                items);

        model.addAttribute(
                "total",
                total);

        model.addAttribute(
                "usuario",
                usuario);

        model.addAttribute(
                "metodosPago",
                metodosPago);

        return "metodo_pago";
    }

    @PostMapping("/pago/procesar")
    public String procesarPago(
            @RequestParam int idMetodo,
            HttpSession session,
            Model model) {

        Usuario usuario
                = (Usuario) session.getAttribute(
                        "usuarioLogueado");

        if (usuario == null) {

            return "redirect:/login";
        }

        CarritoDAO carritoDAO
                = new CarritoDAO();

        CompraDAO compraDAO
                = new CompraDAO();

        DetalleCompraDAO detalleDAO
                = new DetalleCompraDAO();

        ProductoDAO productoDAO
                = new ProductoDAO();

        Carrito carrito
                = carritoDAO.obtenerCarritoPendiente(
                        usuario.getIdUsuario());

        List<ItemCarrito> items
                = cpDAO.obtenerItemsCarrito(
                        usuario.getIdUsuario());

        double total = 0;

        for (ItemCarrito item : items) {

            total += item.getSubtotal();
        }

        Compra compra
                = new Compra();

        compra.setIdUsuario(
                usuario.getIdUsuario());

        compra.setIdCarrito(
                carrito.getIdCarrito());

        compra.setIdMetodo(
                idMetodo);

        compra.setTotal(
                total);

        compra.setEstado(
                "PAGADO");

        int idCompra
                = compraDAO.insertarCompra(
                        compra);

        for (ItemCarrito item : items) {

            detalleDAO.insertarDetalle(
                    idCompra,
                    item.getIdProducto(),
                    item.getCantidad(),
                    item.getPrecio(),
                    item.getSubtotal());

            productoDAO.descontarStock(
                    item.getIdProducto(),
                    item.getCantidad());
        }

        carritoDAO.actualizarEstado(
                carrito.getIdCarrito(),
                "realizado");

        cpDAO.actualizarEstadoCompraCarrito(
                carrito.getIdCarrito(),
                "realizada");

        // Recalcular contador real del carrito
        int cantidadCarrito
                = cpDAO.contarProductosCarrito(
                        usuario.getIdUsuario());

        session.removeAttribute(
                "cantidadCarrito");

        session.setAttribute(
                "cantidadCarrito",
                cantidadCarrito);

        model.addAttribute(
                "items",
                items);

        model.addAttribute(
                "total",
                total);

        return "compra_exitosa";
    }
}
