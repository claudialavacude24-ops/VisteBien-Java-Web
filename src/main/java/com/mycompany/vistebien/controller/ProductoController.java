package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.ProductoDAO;
import com.mycompany.vistebien.model.Producto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

@Controller
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoDAO dao = new ProductoDAO();

    // ✅ Misma ruta que tenías originalmente
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    @GetMapping
    public String listar(@RequestParam(required = false) String action,
            @RequestParam(required = false) String filtro,
            Model model) {
        if ("buscar".equalsIgnoreCase(action) && filtro != null && !filtro.isEmpty()) {
            List<Producto> productos = dao.buscarProducto(filtro);
            if (productos.isEmpty()) {
                model.addAttribute("error", "No se encontraron productos con el filtro: " + filtro);
            }
            model.addAttribute("productos", productos);
        } else {
            model.addAttribute("productos", dao.listarProductosConAdministrador());
        }
        return "producto";
    }

    @PostMapping("/insertar")
    public String insertar(@RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam double precio,
            @RequestParam int stock,
            @RequestParam("imagenFile") MultipartFile imagenFile,
            @RequestParam String categoria,
            @RequestParam int idUsuario,
            Model model) {
        try {
            String nombreArchivo = null;
            if (imagenFile != null && !imagenFile.isEmpty()) {

                // ✅ Validar que sea imagen
                String contentType = imagenFile.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    model.addAttribute("error", "El archivo debe ser una imagen.");
                    return "redirect:/producto";
                }

                // ✅ Nombre limpio sin espacios ni caracteres raros
                String originalName = imagenFile.getOriginalFilename()
                        .replaceAll("\\s+", "_")
                        .replaceAll("[^a-zA-Z0-9._-]", "");
                nombreArchivo = System.currentTimeMillis() + "_" + originalName;

                Path ruta = Paths.get(UPLOAD_DIR, nombreArchivo);
                Files.createDirectories(ruta.getParent());
                Files.copy(imagenFile.getInputStream(), ruta,
                        StandardCopyOption.REPLACE_EXISTING);
            }

            Producto p = new Producto(
                    nombre.trim(),
                    descripcion.trim(),
                    precio,
                    stock,
                    nombreArchivo,
                    categoria.trim(),
                    idUsuario
            );

            dao.insertarProducto(p);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/producto";
    }

    @PostMapping("/actualizar")
    public String actualizar(@RequestParam String id,
            @RequestParam String campo,
            @RequestParam(required = false) String valor,
            @RequestParam(value = "imagenFile", required = false) MultipartFile imagenFile,
            @RequestParam(value = "valorCategoria", required = false) String valorCategoria,
            Model model) {
        try {
            int idNum = Integer.parseInt(id);
            Object nuevoValor = null;

            if ("Precio".equalsIgnoreCase(campo) && valor != null) {
                nuevoValor = Double.parseDouble(valor.replace(",", "."));
            } else if ("Stock".equalsIgnoreCase(campo) && valor != null) {
                nuevoValor = Integer.parseInt(valor);
            } else if ("Imagen".equalsIgnoreCase(campo)
                    && imagenFile != null && !imagenFile.isEmpty()) {

                String contentType = imagenFile.getContentType();
                if (contentType != null && contentType.startsWith("image/")) {
                    // ✅ Nombre limpio
                    String originalName = imagenFile.getOriginalFilename()
                            .replaceAll("\\s+", "_")
                            .replaceAll("[^a-zA-Z0-9._-]", "");
                    String nombreArchivo = System.currentTimeMillis() + "_" + originalName;

                    Path ruta = Paths.get(UPLOAD_DIR, nombreArchivo);
                    Files.createDirectories(ruta.getParent());
                    Files.copy(imagenFile.getInputStream(), ruta,
                            StandardCopyOption.REPLACE_EXISTING);
                    nuevoValor = nombreArchivo;
                }

            } else if ("Categoria".equalsIgnoreCase(campo)
                    && valorCategoria != null && !valorCategoria.isEmpty()) {
                nuevoValor = valorCategoria.trim();
            } else if (valor != null) {
                nuevoValor = valor.trim();
            }

            if (nuevoValor != null) {
                dao.actualizarCampo(idNum, campo, nuevoValor);
            }

            System.out.println("ID recibido: " + idNum);
            System.out.println("Campo recibido: " + campo);
            System.out.println("Nuevo valor procesado: " + nuevoValor);

        } catch (NumberFormatException e) {
            model.addAttribute("error", "El ID ingresado no es válido.");
            e.printStackTrace();
        } catch (IOException e) {
            model.addAttribute("error", "Error al subir la imagen.");
            e.printStackTrace();
        }
        return "redirect:/producto";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam int id, Model model) {
        try {
            dao.eliminarProducto(id);
        } catch (Exception e) {
            model.addAttribute("error", "Error al eliminar producto");
        }
        return "redirect:/producto";
    }
}