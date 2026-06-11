package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.CarritoDAO;
import com.mycompany.vistebien.dao.CarritoProductoDAO;
import com.mycompany.vistebien.model.Carrito;
import com.mycompany.vistebien.model.CarritoProducto;
import com.mycompany.vistebien.model.ItemCarrito;
import com.mycompany.vistebien.model.Usuario;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for shopping cart operations. Uses HttpSession to get the
 * logged-in user (usuarioLogueado).
 *
 * Admin panel routes: GET /carrito → listarAdmin() POST /carrito/eliminar →
 * eliminarCarritoAdmin()
 *
 * User cart routes: GET /carritoCompra/ver → verCarrito() POST /carrito/agregar
 * → agregarProducto() (called from catalogo.jsp) POST
 * /carritoCompra/editarCantidad → editarCantidad() POST /carritoCompra/eliminar
 * → eliminarProducto() POST /carritoCompra/finalizar → finalizarCompra()
 */
@Controller
public class CarritoController {

    private final CarritoDAO carritoDAO = new CarritoDAO();
    private final CarritoProductoDAO cpDAO = new CarritoProductoDAO();

    // =========================================================
    // HELPER — get logged-in user from session
    // =========================================================
    private Usuario getUsuario(HttpSession session) {
        return (Usuario) session.getAttribute("usuarioLogueado");
    }

    // =========================================================
    // VISTA DEL CARRITO — Usuario
    // =========================================================
    /**
     * Displays the user shopping cart. Loads items via JOIN (ItemCarrito has
     * nombre, precio, imagen).
     */
    @GetMapping("/carritoCompra/ver")
    public String verCarrito(HttpSession session, Model model) {

        Usuario usuario = getUsuario(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        int idUsuario = usuario.getIdUsuario();

        List<ItemCarrito> items = cpDAO.obtenerItemsCarrito(idUsuario);

        double total = items.stream()
                .mapToDouble(ItemCarrito::getSubtotal)
                .sum();

        Carrito carrito = carritoDAO.obtenerCarritoPendiente(idUsuario);
        int idCarrito = (carrito != null) ? carrito.getIdCarrito() : -1;

        model.addAttribute("items", items);
        model.addAttribute("total", total);
        model.addAttribute("idCarrito", idCarrito);
        model.addAttribute("cantItems", items.size());

        return "carritoCompras";
    }

    // =========================================================
    // AGREGAR PRODUCTO — desde catálogo
    // =========================================================
    /**
     * Adds a product to the cart (called from catalogo.jsp). Action in
     * catalogo.jsp: action="${contextPath}/carrito/agregar"
     */
    @PostMapping("/carrito/agregar")
    public String agregarProducto(
            @RequestParam int idProducto,
            @RequestParam(defaultValue = "1") int cantidad,
            HttpSession session) {

        Usuario usuario = getUsuario(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        CarritoProducto cp = new CarritoProducto();
        cp.setIdProducto(idProducto);
        cp.setIdUsuario(usuario.getIdUsuario());
        cp.setCantidad(cantidad);

        cpDAO.insertarCarritoProducto(cp);

        // ACTUALIZAR BADGE DEL CARRITO
        int cantidadCarrito
                = cpDAO.contarProductosCarrito(usuario.getIdUsuario());

        session.setAttribute(
                "cantidadCarrito",
                cantidadCarrito);

        String pagina
                = (String) session.getAttribute(
                        "ultimaPagina");

        if (pagina == null) {
            pagina = "/catalogo";
        }

        return "redirect:" + pagina;
    }

    // =========================================================
    // EDITAR CANTIDAD — desde carritoCompras.jsp
    // =========================================================
    /**
     * Updates the quantity of a product in the cart. If quantity is 0 or below,
     * removes the product and deletes the cart header if no items remain.
     */
    @PostMapping("/carritoCompra/editarCantidad")
    public String editarCantidad(
            @RequestParam int idProducto,
            @RequestParam int cantidad,
            HttpSession session) {

        Usuario usuario = getUsuario(session);

        if (usuario == null) {
            return "redirect:/login";
        }

        int idUsuario = usuario.getIdUsuario();

        Carrito carrito
                = carritoDAO.obtenerCarritoPendiente(idUsuario);

        if (carrito == null) {
            return "redirect:/carritoCompra/ver";
        }

        int idCarrito = carrito.getIdCarrito();

        if (cantidad <= 0) {

            cpDAO.eliminarCarritoProducto(
                    idCarrito,
                    idProducto);

            List<ItemCarrito> restantes
                    = cpDAO.obtenerItemsCarrito(idUsuario);

            if (restantes.isEmpty()) {

                carritoDAO.eliminarCarrito(idCarrito);
            }

        } else {

            cpDAO.actualizarCarritoProducto(
                    idCarrito,
                    idProducto,
                    cantidad);
        }

        int cantidadCarrito
                = cpDAO.contarProductosCarrito(idUsuario);

        session.setAttribute(
                "cantidadCarrito",
                cantidadCarrito);

        return "redirect:/carritoCompra/ver";
    }

    // =========================================================
    // ELIMINAR PRODUCTO — desde carritoCompras.jsp
    // =========================================================
    /**
     * Removes a product from the cart. Deletes the cart header if no items
     * remain after removal.
     */
    @PostMapping("/carritoCompra/eliminar")
    public String eliminarProducto(
            @RequestParam int idCarrito,
            @RequestParam int idProducto,
            HttpSession session) {

        Usuario usuario = getUsuario(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        cpDAO.eliminarCarritoProducto(idCarrito, idProducto);

        List<ItemCarrito> restantes = cpDAO.obtenerItemsCarrito(usuario.getIdUsuario());
        if (restantes.isEmpty()) {
            carritoDAO.eliminarCarrito(idCarrito);
        }

        int cantidadCarrito
                = cpDAO.contarProductosCarrito(usuario.getIdUsuario());

        session.setAttribute(
                "cantidadCarrito",
                cantidadCarrito);

        return "redirect:/carritoCompra/ver";
    }

    // =========================================================
    // FINALIZAR COMPRA
    // =========================================================
    /**
     * Finalizes the purchase. Updates the cart status and all its products to
     * 'completado'.
     */
    @PostMapping("/carritoCompra/finalizar")
    public String finalizarCompra(
            @RequestParam int idCarrito,
            HttpSession session,
            Model model) {

        Usuario usuario = getUsuario(session);
        if (usuario == null) {
            return "redirect:/login";
        }

        carritoDAO.actualizarEstado(idCarrito, "completado");
        cpDAO.actualizarEstadoCompraCarrito(idCarrito, "completado");
        session.setAttribute("cantidadCarrito", 0);

        model.addAttribute("items", new ArrayList<>());
        model.addAttribute("total", 0.0);
        model.addAttribute("idCarrito", -1);
        model.addAttribute("cantItems", 0);
        model.addAttribute("compraFinalizada", true);
        model.addAttribute("mensaje",
                "¡Compra finalizada exitosamente! Tu pedido está en camino.");

        return "carritoCompras";
    }

    // =========================================================
    // PANEL ADMIN — /carrito
    // =========================================================
    /**
     * Admin panel: lists all carts. Clients are redirected to the user cart
     * view.
     */
    @GetMapping("/carrito")
    public String listarAdmin(
            @RequestParam(required = false) String filtro,
            HttpSession session,
            Model model) {

        String rol = (String) session.getAttribute("rol");
        if (rol == null) {
            return "redirect:/login";
        }

        if ("cliente".equals(rol)) {
            return "redirect:/carritoCompra/ver";
        }

        if (filtro != null && !filtro.trim().isEmpty()) {
            model.addAttribute("carritos", carritoDAO.buscarCarrito(filtro));
            model.addAttribute("filtro", filtro);
        } else {
            model.addAttribute("carritos", carritoDAO.listarCarritos());
        }

        return "carrito";
    }

    /**
     * Admin panel: deletes a cart and its products.
     */
    @PostMapping("/carrito/eliminar")
    public String eliminarCarritoAdmin(
            @RequestParam String id,
            HttpSession session,
            Model model) {

        String rol = (String) session.getAttribute("rol");
        if (!"admin".equals(rol)) {
            return "redirect:/login";
        }

        try {
            int idNum = Integer.parseInt(id.trim());
            carritoDAO.eliminarCarrito(idNum);
        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido. Solo se permiten números.");
            model.addAttribute("carritos", carritoDAO.listarCarritos());
            return "carrito";
        }

        return "redirect:/carrito";
    }
}
