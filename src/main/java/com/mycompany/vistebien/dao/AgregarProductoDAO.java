package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.AgregarProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AgregarProductoDAO {

    // Insertar registro en la tabla agregar_producto
    public void insertarAgregarProducto(AgregarProducto ap) {
        String sql = "INSERT INTO agregar_producto (IdProducto, IdUsuario, Cantidad, Imagen) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, ap.getIdProducto());
            stmt.setInt(2, ap.getIdUsuario());
            stmt.setInt(3, ap.getCantidad());
            stmt.setString(4, ap.getImagen()); // nombre del archivo de imagen
            stmt.executeUpdate();
            System.out.println("Registro insertado en agregar_producto");

        } catch (SQLException e) {
            System.out.println("Error al insertar en agregar_producto");
            e.printStackTrace();
        }
    }

    // Listar registros
    public List<AgregarProducto> listarAgregarProductos() {
        List<AgregarProducto> lista = new ArrayList<>();
        String sql = "SELECT IdAgregarProducto, IdProducto, IdUsuario, Cantidad, Imagen FROM agregar_producto";

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                AgregarProducto ap = new AgregarProducto();
                ap.setIdAgregarProducto(rs.getInt("IdAgregarProducto"));
                ap.setIdProducto(rs.getInt("IdProducto"));
                ap.setIdUsuario(rs.getInt("IdUsuario"));
                ap.setCantidad(rs.getInt("Cantidad"));
                ap.setImagen(rs.getString("Imagen"));
                lista.add(ap);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar registros de agregar_producto");
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar registro
    public void eliminarAgregarProducto(int idAgregarProducto) {
        String sql = "DELETE FROM agregar_producto WHERE IdAgregarProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAgregarProducto);
            stmt.executeUpdate();
            System.out.println("Registro eliminado de agregar_producto");

        } catch (SQLException e) {
            System.out.println("Error al eliminar de agregar_producto");
            e.printStackTrace();
        }
    }
}
