package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    // =====================================
    // INSERTAR ADMINISTRADOR
    // =====================================
    public boolean insertarAdministrador(Administrador admin) {

        String sql = "INSERT INTO administrador (Nombre, Correo, Contrasena, Telefono) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getCorreo());
            stmt.setString(3, admin.getContrasena());
            stmt.setString(4, admin.getTelefono());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar administrador");
            e.printStackTrace();
        }

        return false;
    }

    // =====================================
    // VALIDAR CORREO
    // =====================================
    public boolean existeCorreo(String correo) {

        String sql
                = "SELECT COUNT(*) "
                + "FROM administrador "
                + "WHERE Correo=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {

            System.out.println("Error validando correo");
            e.printStackTrace();
        }

        return false;
    }

    // =====================================
    // LOGIN ADMINISTRADOR
    // =====================================
    public Administrador login(
            String correo,
            String contrasena) {

        String sql
                = "SELECT * FROM administrador "
                + "WHERE Correo=? "
                + "AND Contrasena=?";

        try (
                Connection conn
                = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs
                    = stmt.executeQuery();

            if (rs.next()) {

                Administrador admin
                        = new Administrador();

                admin.setIdAdministrador(
                        rs.getInt("IdAdministrador"));

                admin.setNombre(
                        rs.getString("Nombre"));

                admin.setCorreo(
                        rs.getString("Correo"));

                admin.setContrasena(
                        rs.getString("Contrasena"));

                admin.setTelefono(
                        rs.getString("Telefono"));

                return admin;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    // =====================================
    // BUSCAR ADMINISTRADOR
    // =====================================
    public List<Administrador> buscarAdministrador(String filtro) {

        List<Administrador> lista = new ArrayList<>();

        String sql
                = "SELECT * FROM administrador "
                + "WHERE CAST(IdAdministrador AS CHAR) LIKE ? "
                + "OR Nombre LIKE ? "
                + "OR Correo LIKE ? "
                + "OR Telefono LIKE ?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeFiltro = "%" + filtro + "%";

            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);
            stmt.setString(3, likeFiltro);
            stmt.setString(4, likeFiltro);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Administrador admin = new Administrador();

                admin.setIdAdministrador(
                        rs.getInt("IdAdministrador"));

                admin.setNombre(
                        rs.getString("Nombre"));

                admin.setCorreo(
                        rs.getString("Correo"));

                admin.setContrasena(
                        rs.getString("Contrasena"));

                admin.setTelefono(
                        rs.getString("Telefono"));

                lista.add(admin);
            }

        } catch (SQLException e) {

            System.out.println("Error al buscar administrador");
            e.printStackTrace();
        }

        return lista;
    }

    // =====================================
    // LISTAR ADMINISTRADORES
    // =====================================
    public List<Administrador> listarAdministradores() {

        List<Administrador> lista = new ArrayList<>();

        String sql = "SELECT * FROM administrador";

        try (
                Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Administrador admin = new Administrador();

                admin.setIdAdministrador(
                        rs.getInt("IdAdministrador"));

                admin.setNombre(
                        rs.getString("Nombre"));

                admin.setCorreo(
                        rs.getString("Correo"));

                admin.setContrasena(
                        rs.getString("Contrasena"));

                admin.setTelefono(
                        rs.getString("Telefono"));

                lista.add(admin);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar administradores");
            e.printStackTrace();
        }

        return lista;
    }

    // =====================================
    // ACTUALIZAR CAMPO
    // =====================================
    public void actualizarCampo(
            int id,
            String campo,
            String valor) {

        String sql
                = "UPDATE administrador "
                + "SET " + campo + "=? "
                + "WHERE IdAdministrador=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, valor);
            stmt.setInt(2, id);

            stmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Error actualizando administrador");
            e.printStackTrace();
        }
    }

    // =====================================
    // ACTUALIZAR ADMINISTRADOR
    // =====================================
    public void actualizarAdministrador(
            Administrador admin) {

        String sql
                = "UPDATE administrador "
                + "SET Nombre=?, "
                + "Correo=?, "
                + "Contrasena=?, "
                + "Telefono=? "
                + "WHERE IdAdministrador=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getCorreo());
            stmt.setString(3, admin.getContrasena());
            stmt.setString(4, admin.getTelefono());
            stmt.setInt(5, admin.getIdAdministrador());

            stmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Error actualizando administrador");
            e.printStackTrace();
        }
    }

    // =====================================
    // ELIMINAR ADMINISTRADOR
    // =====================================
    public void eliminarAdministrador(int id) {

        String sql
                = "DELETE FROM administrador "
                + "WHERE IdAdministrador=?";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Error eliminando administrador");
            e.printStackTrace();
        }
    }
}
