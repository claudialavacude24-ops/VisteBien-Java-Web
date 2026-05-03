package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.ProductoDAO;
import com.mycompany.vistebien.model.Producto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/api/producto")
public class ProductoApiController {

    private final ProductoDAO dao = new ProductoDAO();

    // Listar productos
    @GetMapping
    public List<Producto> listar(@RequestParam(required = false) String filtro) {
        if (filtro != null && !filtro.isEmpty()) {
            return dao.buscarProducto(filtro);
        } else {
            return dao.listarProductosConAdministrador();
        }
    }

    // Insertar producto con imagen (form-data)
    @PostMapping("/insertar")
    public ResponseEntity<String> insertar(@RequestBody Producto p) {
        try {
            String nombreArchivo = null;
            if (p.getImagen() != null && !p.getImagen().isEmpty()) {
                // La ruta completa viene en el JSON, ej: C:/Users/Johan/.../camisa3.jpg
                Path origen = Paths.get(p.getImagen());

                // Generamos un nombre único para guardar en uploads
                nombreArchivo = System.currentTimeMillis() + "_" + origen.getFileName().toString();
                Path destino = Paths.get("src/main/resources/static/uploads/", nombreArchivo);

                // Creamos carpeta si no existe y copiamos el archivo
                Files.createDirectories(destino.getParent());
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

                // Guardamos solo el nombre en la BD
                p.setImagen(nombreArchivo);
            }

            dao.insertarProducto(p);
            return ResponseEntity.ok("Producto insertado correctamente con imagen");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al copiar la imagen desde la ruta local");
        }
    }

    // Actualizar producto con JSON (texto/números)
    @PutMapping("/actualizar-json/{id}")
    public ResponseEntity<String> actualizarJson(@PathVariable int id,
            @RequestBody Producto datos) {
        try {
            // Actualizamos campos de texto/números
            dao.actualizarCampo(id, "Nombre", datos.getNombre());
            dao.actualizarCampo(id, "Descripcion", datos.getDescripcion());
            dao.actualizarCampo(id, "Precio", datos.getPrecio());
            dao.actualizarCampo(id, "Stock", datos.getStock());
            dao.actualizarCampo(id, "Categoria", datos.getCategoria());
            dao.actualizarCampo(id, "IdUsuario", datos.getIdUsuario());

            // Si viene ruta de imagen en el JSON
            if (datos.getImagen() != null && !datos.getImagen().isEmpty()) {
                Path origen = Paths.get(datos.getImagen()); // ruta local enviada
                String nombreArchivo = System.currentTimeMillis() + "_" + origen.getFileName().toString();
                Path destino = Paths.get("src/main/resources/static/uploads/", nombreArchivo);

                Files.createDirectories(destino.getParent());
                Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);

                // Guardamos solo el nombre en BD
                dao.actualizarCampo(id, "Imagen", nombreArchivo);
            }

            return ResponseEntity.ok("Producto actualizado correctamente");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al copiar la imagen desde la ruta local");
        }
    }

    // Eliminar producto
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        if (!dao.existeProducto(id)) {
            return ResponseEntity.badRequest().body("El producto con ID " + id + " no existe");
        }
        dao.eliminarProducto(id);
        return ResponseEntity.ok("Producto eliminado correctamente");
    }
}
