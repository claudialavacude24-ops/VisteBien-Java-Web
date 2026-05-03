package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // Insertar usuario
    public void insertarUsuario(Usuario u) {
        String sql = "INSERT INTO usuario (Nombre, Correo, Contrasena, Telefono, Direccion) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getTelefono());
            stmt.setString(5, u.getDireccion());

            stmt.executeUpdate();
            System.out.println("Usuario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario");
            e.printStackTrace();
        }
    }

    // Listar usuarios
    public List<Usuario> listarUsuarios() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setNombre(rs.getString("Nombre"));
                u.setCorreo(rs.getString("Correo"));
                u.setContrasena(rs.getString("Contrasena"));
                u.setTelefono(rs.getString("Telefono"));
                u.setDireccion(rs.getString("Direccion"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar usuarios");
            e.printStackTrace();
        }

        return lista;
    }

    // Actualizar campo (validando nombre de columna)
    public void actualizarCampo(int idUsuario, String campo, Object valor) {
        // Validar que el campo sea permitido
        List<String> camposPermitidos = List.of("Nombre", "Correo", "Contrasena", "Telefono", "Direccion");
        if (!camposPermitidos.contains(campo)) {
            throw new IllegalArgumentException("Campo no válido: " + campo);
        }

        String sql = "UPDATE usuario SET " + campo + " = ? WHERE IdUsuario = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, valor);
            stmt.setInt(2, idUsuario);
            stmt.executeUpdate();

            System.out.println("Campo actualizado correctamente en usuario");

        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario");
            e.printStackTrace();
        }
    }

    public void actualizarUsuario(Usuario u) {
        String sql = "UPDATE usuario SET Nombre=?, Correo=?, Contrasena=?, Telefono=?, Direccion=? WHERE IdUsuario=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getTelefono());
            stmt.setString(5, u.getDireccion());
            stmt.setInt(6, u.getIdUsuario());
            stmt.executeUpdate();
            System.out.println("Usuario actualizado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al actualizar usuario");
            e.printStackTrace();
        }
    }

    // Eliminar usuario
    public void eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM usuario WHERE IdUsuario=?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();

            System.out.println("Usuario eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario");
            e.printStackTrace();
        }
    }

    // Validar existencia de usuario
    public boolean existeUsuario(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE IdUsuario=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error al validar existencia de usuario");
            e.printStackTrace();
        }
        return false;
    }

    // Buscar usuarios por IdUsuario, Nombre, Correo o Teléfono
    public List<Usuario> buscarUsuario(String filtro) {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario "
                + "WHERE CAST(IdUsuario AS CHAR) LIKE ? "
                + "OR Nombre LIKE ? "
                + "OR Correo LIKE ? "
                + "OR Telefono LIKE ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeFiltro = "%" + filtro + "%";
            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);
            stmt.setString(3, likeFiltro);
            stmt.setString(4, likeFiltro);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("IdUsuario"));
                u.setNombre(rs.getString("Nombre"));
                u.setCorreo(rs.getString("Correo"));
                u.setContrasena(rs.getString("Contrasena"));
                u.setTelefono(rs.getString("Telefono"));
                u.setDireccion(rs.getString("Direccion"));
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar usuario");
            e.printStackTrace();
        }
        return lista;
    }
}
