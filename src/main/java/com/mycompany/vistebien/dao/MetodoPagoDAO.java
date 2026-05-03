package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.MetodoPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MetodoPagoDAO {

    // Insertar un registro en la tabla metodo_pago
    public void insertarMetodoPago(MetodoPago mp) {
        String sql = "INSERT INTO metodo_pago (Nombre) VALUES (?)";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, mp.getNombre());
            stmt.executeUpdate();

            System.out.println("Método de pago insertado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Listar todos los registros de la tabla metodo_pago
    public List<MetodoPago> listarMetodosPago() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM metodo_pago";

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setIdMetodo(rs.getInt("IdMetodo"));
                mp.setNombre(rs.getString("Nombre"));
                lista.add(mp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    // Actualizar el campo Nombre
    public void actualizarNombre(int idMetodo, String nuevoNombre) {
        String sql = "UPDATE metodo_pago SET Nombre = ? WHERE IdMetodo = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoNombre);
            stmt.setInt(2, idMetodo);

            stmt.executeUpdate();
            System.out.println("Nombre del método de pago actualizado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Eliminar un registro de la tabla metodo_pago
    public void eliminarMetodoPago(int idMetodo) {
        String sql = "DELETE FROM metodo_pago WHERE IdMetodo=?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idMetodo);
            stmt.executeUpdate();

            System.out.println("Método de pago eliminado correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
