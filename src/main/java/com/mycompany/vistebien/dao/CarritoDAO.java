package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Carrito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    // Insertar carrito
    public void insertarCarrito(Carrito carrito) {
        String sql = "INSERT INTO carrito (IdUsuario) VALUES (?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, carrito.getIdUsuario());
            stmt.executeUpdate();
            System.out.println("Carrito insertado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al insertar carrito");
            e.printStackTrace();
        }
    }

    // Listar carritos
    public List<Carrito> listarCarritos() {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrito";
        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Carrito c = new Carrito();
                c.setIdCarrito(rs.getInt("IdCarrito"));
                c.setIdUsuario(rs.getInt("IdUsuario"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar carritos");
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar carrito
    public void eliminarCarrito(int idCarrito) {
        String sql = "DELETE FROM carrito WHERE IdCarrito=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCarrito);
            stmt.executeUpdate();
            System.out.println("Carrito eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar carrito");
            e.printStackTrace();
        }
    }

    // Buscar carritos
    public List<Carrito> buscarCarrito(String filtro) {
        List<Carrito> lista = new ArrayList<>();
        String sql = "SELECT * FROM carrito WHERE CAST(IdCarrito AS CHAR) LIKE ? OR CAST(IdUsuario AS CHAR) LIKE ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            String likeFiltro = "%" + filtro + "%";
            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Carrito c = new Carrito();
                c.setIdCarrito(rs.getInt("IdCarrito"));
                c.setIdUsuario(rs.getInt("IdUsuario"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar carrito");
            e.printStackTrace();
        }
        return lista;
    }
}
