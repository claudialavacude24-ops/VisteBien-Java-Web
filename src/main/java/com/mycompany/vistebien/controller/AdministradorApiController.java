package com.mycompany.vistebien.controller;

import com.mycompany.vistebien.dao.AdministradorDAO;
import com.mycompany.vistebien.model.Administrador;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrador")
public class AdministradorApiController {

    private AdministradorDAO dao = new AdministradorDAO();

    // Listar administradores
    @GetMapping
    public List<Administrador> listar(@RequestParam(required = false) String filtro) {
        if (filtro != null && !filtro.isEmpty()) {
            return dao.buscarAdministrador(filtro);
        } else {
            return dao.listarAdministradores();
        }
    }

    // Insertar administrador
    @PostMapping("/insertar")
    public Administrador insertar(@RequestBody Administrador a) {
        dao.insertarAdministrador(a);
        return a;
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Administrador> actualizar(@PathVariable int id,
            @RequestBody Administrador datos) {
        datos.setIdAdministrador(id);
        dao.actualizarAdministrador(datos);
        return ResponseEntity.ok(datos);
    }

    // Eliminar administrador
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        dao.eliminarAdministrador(id);
        return ResponseEntity.ok("Administrador eliminado correctamente");
    }
}
