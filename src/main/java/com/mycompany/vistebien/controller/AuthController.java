package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.AdministradorDAO;
import com.mycompany.vistebien.dao.UsuarioDAO;
import com.mycompany.vistebien.model.Administrador;
import com.mycompany.vistebien.model.Usuario;
import com.mycompany.vistebien.dao.CarritoProductoDAO;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final AdministradorDAO administradorDAO = new AdministradorDAO();

    private final CarritoProductoDAO cpDAO = new CarritoProductoDAO();

    // =====================================
    // MOSTRAR LOGIN
    // =====================================
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    // =====================================
    // MOSTRAR REGISTRO
    // =====================================
    @GetMapping("/registro")
    public String mostrarRegistro() {
        return "registro";
    }

    // =====================================
    // REGISTRAR USUARIO
    // =====================================
    @PostMapping("/registro")
    public String registrarUsuario(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String contrasena,
            @RequestParam String confirmarContrasena,
            @RequestParam(required = false) String telefono,
            @RequestParam(required = false) String direccion,
            Model model) {

        try {

            nombre = nombre.trim();
            correo = correo.trim();

            if (nombre.isEmpty()) {
                model.addAttribute("error",
                        "Debe ingresar el nombre");
                return "registro";
            }

            if (!correo.matches(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

                model.addAttribute(
                        "error",
                        "Correo electrónico inválido");

                return "registro";
            }

            if (contrasena.length() < 6) {

                model.addAttribute(
                        "error",
                        "La contraseña debe tener mínimo 6 caracteres");

                return "registro";
            }

            if (!contrasena.equals(confirmarContrasena)) {

                model.addAttribute(
                        "error",
                        "Las contraseñas no coinciden");

                return "registro";
            }

            if (usuarioDAO.existeCorreo(correo)) {

                model.addAttribute(
                        "error",
                        "El correo ya se encuentra registrado");

                return "registro";
            }

            Usuario usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setContrasena(contrasena);
            usuario.setTelefono(telefono);
            usuario.setDireccion(direccion);

            boolean registrado
                    = usuarioDAO.registrarUsuario(usuario);

            if (!registrado) {

                model.addAttribute(
                        "error",
                        "No fue posible registrar el usuario");

                return "registro";
            }

            model.addAttribute(
                    "success",
                    "Usuario registrado correctamente");

            return "login";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Error interno al registrar usuario");

            return "registro";
        }
    }

    // =====================================
    // LOGIN
    // =====================================
    @PostMapping("/login")
    public String login(
            @RequestParam String correo,
            @RequestParam String contrasena,
            HttpSession session,
            Model model) {

        try {

            // ==========================
            // ADMINISTRADOR
            // ==========================
            Administrador admin
                    = administradorDAO.login(
                            correo,
                            contrasena);

            if (admin != null) {

                session.setAttribute(
                        "rol",
                        "admin");

                session.setAttribute(
                        "adminLogueado",
                        admin);

                session.setAttribute(
                        "nombreUsuario",
                        admin.getNombre());

                // NUEVO
                session.setAttribute(
                        "idAdministrador",
                        admin.getIdAdministrador());

                return "redirect:/index";
            }

// ==========================
// CLIENTE
// ==========================
            Usuario usuario
                    = usuarioDAO.login(
                            correo,
                            contrasena);

            if (usuario != null) {

                session.setAttribute(
                        "rol",
                        "cliente");

                session.setAttribute(
                        "usuarioLogueado",
                        usuario);

                session.setAttribute(
                        "nombreUsuario",
                        usuario.getNombre());

                session.setAttribute(
                        "idUsuario",
                        usuario.getIdUsuario());

                // =====================================
                // CARGAR CANTIDAD DE PRODUCTOS CARRITO
                // =====================================
                int cantidadCarrito
                        = cpDAO.contarProductosCarrito(
                                usuario.getIdUsuario());

                session.setAttribute(
                        "cantidadCarrito",
                        cantidadCarrito);

                return "redirect:/index";
            }

            model.addAttribute(
                    "error",
                    "Correo o contraseña incorrectos");

            return "login";

        } catch (Exception e) {

            e.printStackTrace();

            model.addAttribute(
                    "error",
                    "Ocurrió un error al iniciar sesión");

            return "login";
        }
    }

    // =====================================
    // CERRAR SESIÓN
    // =====================================
    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/index";
    }
}
