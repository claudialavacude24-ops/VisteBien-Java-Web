package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.UsuarioDAO;
import com.mycompany.vistebien.model.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioApiController {

    private UsuarioDAO dao = new UsuarioDAO();

    // Listar usuarios (con filtro opcional)
    @GetMapping
    public List<Usuario> listar(@RequestParam(required = false) String filtro) {
        if (filtro != null && !filtro.isEmpty()) {
            return dao.buscarUsuario(filtro);
        } else {
            return dao.listarUsuarios();
        }
    }

    // Insertar usuario
    @PostMapping("/insertar")
    public Usuario insertar(@RequestBody Usuario u) {
        dao.insertarUsuario(u);
        return u;
    }

    // Actualizar usuario completo (validando ID)
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id,
            @RequestBody Usuario datos) {
        if (!dao.existeUsuario(id)) {
            return ResponseEntity.badRequest().body("El usuario con ID " + id + " no existe");
        }
        datos.setIdUsuario(id);
        dao.actualizarUsuario(datos); // método que actualiza todos los campos
        return ResponseEntity.ok(datos);
    }

    // Eliminar usuario (validando ID)
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        if (!dao.existeUsuario(id)) {
            return ResponseEntity.badRequest().body("El usuario con ID " + id + " no existe");
        }
        dao.eliminarUsuario(id);
        return ResponseEntity.ok("Usuario eliminado correctamente");
    }
}
