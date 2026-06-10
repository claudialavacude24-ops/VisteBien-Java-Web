package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // =========================
    // INSERTAR USUARIO
    public boolean insertarUsuario(Usuario u) {

        String sql
                = "INSERT INTO usuario "
                + "(Nombre, Correo, Contrasena, Telefono, Direccion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getTelefono());
            stmt.setString(5, u.getDireccion());

            int filas = stmt.executeUpdate();

            return filas > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar usuario");
            e.printStackTrace();

            return false;
        }
    }

    // =========================
    // LISTAR USUARIOS
    // =========================
    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario";

        try (
                Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

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

    // =========================
    // ACTUALIZAR CAMPO
    // =========================
    public void actualizarCampo(
            int idUsuario,
            String campo,
            Object valor) {

        List<String> camposPermitidos = List.of(
                "Nombre",
                "Correo",
                "Contrasena",
                "Telefono",
                "Direccion"
        );

        if (!camposPermitidos.contains(campo)) {
            throw new IllegalArgumentException(
                    "Campo no válido: " + campo);
        }

        String sql = "UPDATE usuario SET "
                + campo
                + " = ? WHERE IdUsuario=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, valor);
            stmt.setInt(2, idUsuario);

            stmt.executeUpdate();

            System.out.println(
                    "Campo actualizado correctamente");

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario");

            e.printStackTrace();
        }
    }

    // =========================
    // ACTUALIZAR USUARIO
    // =========================
    public void actualizarUsuario(Usuario u) {

        String sql = "UPDATE usuario "
                + "SET Nombre=?, "
                + "Correo=?, "
                + "Contrasena=?, "
                + "Telefono=?, "
                + "Direccion=? "
                + "WHERE IdUsuario=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, u.getNombre());
            stmt.setString(2, u.getCorreo());
            stmt.setString(3, u.getContrasena());
            stmt.setString(4, u.getTelefono());
            stmt.setString(5, u.getDireccion());
            stmt.setInt(6, u.getIdUsuario());

            stmt.executeUpdate();

            System.out.println(
                    "Usuario actualizado correctamente");

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario");

            e.printStackTrace();
        }
    }

    // =========================
    // ELIMINAR USUARIO
    // =========================
    public void eliminarUsuario(int idUsuario) {

        String sql
                = "DELETE FROM usuario WHERE IdUsuario=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            stmt.executeUpdate();

            System.out.println(
                    "Usuario eliminado correctamente");

        } catch (SQLException e) {

            System.out.println(
                    "Error al eliminar usuario");

            e.printStackTrace();
        }
    }

    // =========================
    // EXISTE USUARIO
    // =========================
    public boolean existeUsuario(int idUsuario) {

        String sql
                = "SELECT COUNT(*) FROM usuario "
                + "WHERE IdUsuario=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al validar usuario");

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // EXISTE CORREO
    // =========================
    public boolean existeCorreo(String correo) {

        String sql
                = "SELECT * FROM usuario "
                + "WHERE Correo=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return false;
    }

    // =========================
    // LOGIN
    // =========================
    public Usuario login(
            String correo,
            String contrasena) {

        String sql
                = "SELECT * FROM usuario "
                + "WHERE Correo=? "
                + "AND Contrasena=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario u = new Usuario();

                u.setIdUsuario(
                        rs.getInt("IdUsuario"));

                u.setNombre(
                        rs.getString("Nombre"));

                u.setCorreo(
                        rs.getString("Correo"));

                u.setContrasena(
                        rs.getString("Contrasena"));

                u.setTelefono(
                        rs.getString("Telefono"));

                u.setDireccion(
                        rs.getString("Direccion"));

                return u;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================
    // BUSCAR USUARIO
    // =========================
    public List<Usuario> buscarUsuario(String filtro) {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT * FROM usuario "
                + "WHERE CAST(IdUsuario AS CHAR) LIKE ? "
                + "OR Nombre LIKE ? "
                + "OR Correo LIKE ? "
                + "OR Telefono LIKE ?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like = "%" + filtro + "%";

            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);
            stmt.setString(4, like);

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

            System.out.println(
                    "Error al buscar usuario");

            e.printStackTrace();
        }

        return lista;
    }

    // =========================
// REGISTRAR USUARIO WEB
// =========================
    public boolean registrarUsuario(Usuario usuario) {

        if (usuario == null) {
            return false;
        }

        if (usuario.getNombre() == null
                || usuario.getNombre().trim().isEmpty()) {
            return false;
        }

        if (usuario.getCorreo() == null
                || usuario.getCorreo().trim().isEmpty()) {
            return false;
        }

        if (usuario.getContrasena() == null
                || usuario.getContrasena().trim().isEmpty()) {
            return false;
        }

        if (existeCorreo(usuario.getCorreo())) {
            return false;
        }

        return insertarUsuario(usuario);
    }
}
