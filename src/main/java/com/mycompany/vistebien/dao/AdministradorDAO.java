package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Administrador;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {

    // Insertar administrador
    public void insertarAdministrador(Administrador admin) {
        String sql = "INSERT INTO administrador (Nombre, Correo, Contrasena, Telefono) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getCorreo());
            stmt.setString(3, admin.getContrasena());
            stmt.setString(4, admin.getTelefono());

            stmt.executeUpdate();
            System.out.println("SQL ejecutado: " + sql);
            System.out.println("Administrador insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar administrador");
            e.printStackTrace();
        }
    }

    // Buscar administrador por ID, Nombre, Correo o Teléfono
    public List<Administrador> buscarAdministrador(String filtro) {
        List<Administrador> lista = new ArrayList<>();

        String sql = "SELECT IdAdministrador, Nombre, Correo, Contrasena, Telefono "
                + "FROM administrador "
                + "WHERE CAST(IdAdministrador AS CHAR) LIKE ? "
                + "OR Nombre LIKE ? "
                + "OR Correo LIKE ? "
                + "OR Telefono LIKE ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeFiltro = "%" + filtro + "%";
            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);
            stmt.setString(3, likeFiltro);
            stmt.setString(4, likeFiltro);

            System.out.println("SQL ejecutado: " + sql);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Administrador admin = new Administrador();
                admin.setIdAdministrador(rs.getInt("IdAdministrador"));
                admin.setNombre(rs.getString("Nombre"));
                admin.setCorreo(rs.getString("Correo"));
                admin.setContrasena(rs.getString("Contrasena"));
                admin.setTelefono(rs.getString("Telefono"));
                lista.add(admin);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar administrador");
            e.printStackTrace();
        }

        return lista;
    }

    // Listar todos los administradores
    public List<Administrador> listarAdministradores() {
        List<Administrador> lista = new ArrayList<>();
        String sql = "SELECT * FROM administrador";

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("SQL ejecutado: " + sql);

            while (rs.next()) {
                Administrador admin = new Administrador();
                admin.setIdAdministrador(rs.getInt("IdAdministrador"));
                admin.setNombre(rs.getString("Nombre"));
                admin.setCorreo(rs.getString("Correo"));
                admin.setContrasena(rs.getString("Contrasena"));
                admin.setTelefono(rs.getString("Telefono"));
                lista.add(admin);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar administradores");
            e.printStackTrace();
        }

        return lista;
    }

    // Actualizar campo específico (para JSP)
    public void actualizarCampo(int id, String campo, String valor) {
        String sql = "UPDATE administrador SET " + campo + " = ? WHERE IdAdministrador = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, valor);
            stmt.setInt(2, id);

            stmt.executeUpdate();
            System.out.println("SQL ejecutado: " + sql);
            System.out.println("Campo actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar administrador");
            e.printStackTrace();
        }
    }

    // Actualizar administrador completo (para API REST)
    public void actualizarAdministrador(Administrador admin) {
        String sql = "UPDATE administrador SET Nombre=?, Correo=?, Contrasena=?, Telefono=? WHERE IdAdministrador=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getNombre());
            stmt.setString(2, admin.getCorreo());
            stmt.setString(3, admin.getContrasena());
            stmt.setString(4, admin.getTelefono());
            stmt.setInt(5, admin.getIdAdministrador());

            stmt.executeUpdate();
            System.out.println("SQL ejecutado: " + sql);
            System.out.println("Administrador actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar administrador");
            e.printStackTrace();
        }
    }

    // Eliminar administrador
    public void eliminarAdministrador(int id) {
        String sql = "DELETE FROM administrador WHERE IdAdministrador=?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            stmt.executeUpdate();
            System.out.println("SQL ejecutado: " + sql);
            System.out.println("Administrador eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar administrador");
            e.printStackTrace();
        }
    }

}
