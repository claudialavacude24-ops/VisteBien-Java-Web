package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    public void insertarProducto(Producto p) {
        String sql = "INSERT INTO producto (Nombre, Descripcion, Precio, Stock, Imagen, Categoria, IdUsuario) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, p.getNombre().trim());
            stmt.setString(2, p.getDescripcion().trim());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getImagen());
            stmt.setString(6, p.getCategoria().trim());
            stmt.setInt(7, p.getIdUsuario());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> listarProductosConAdministrador() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("IdProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                p.setImagen(rs.getString("Imagen"));
                p.setCategoria(rs.getString("Categoria"));
                p.setIdUsuario(rs.getInt("IdUsuario"));
                lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

// Actualizar campo específico de producto
    public void actualizarCampo(int id, String campo, Object valor) {
        String sql = "UPDATE producto SET " + campo + " = ? WHERE IdProducto = ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            if ("Precio".equalsIgnoreCase(campo)) {
                stmt.setDouble(1, Double.parseDouble(valor.toString().replace(",", ".")));
            } else if ("Stock".equalsIgnoreCase(campo)) {
                stmt.setInt(1, Integer.parseInt(valor.toString()));
            } else {
                stmt.setString(1, valor.toString().trim());
            }

            stmt.setInt(2, id);
            stmt.executeUpdate();

            System.out.println("SQL ejecutado: " + sql);
            System.out.println("Campo actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto");
            e.printStackTrace();
        }
    }

    // ✅ Nuevo método: actualizar producto completo
    public void actualizarProducto(Producto p) {
        String sql = "UPDATE producto SET Nombre=?, Descripcion=?, Precio=?, Stock=?, Imagen=?, Categoria=?, IdUsuario=? WHERE IdProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, p.getNombre());
            stmt.setString(2, p.getDescripcion());
            stmt.setDouble(3, p.getPrecio());
            stmt.setInt(4, p.getStock());
            stmt.setString(5, p.getImagen());
            stmt.setString(6, p.getCategoria());
            stmt.setInt(7, p.getIdUsuario());
            stmt.setInt(8, p.getIdProducto());

            stmt.executeUpdate();
            System.out.println("Producto actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al actualizar producto");
            e.printStackTrace();
        }
    }

    public void eliminarProducto(int idProducto) {
        String sql = "DELETE FROM producto WHERE IdProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> buscarProducto(String filtro) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto "
                + "WHERE CAST(IdProducto AS CHAR) LIKE ? "
                + "OR Nombre LIKE ? "
                + "OR Descripcion LIKE ? "
                + "OR Categoria LIKE ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeFiltro = "%" + filtro + "%";
            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);
            stmt.setString(3, likeFiltro);
            stmt.setString(4, likeFiltro);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("IdProducto"));
                p.setNombre(rs.getString("Nombre"));
                p.setDescripcion(rs.getString("Descripcion"));
                p.setPrecio(rs.getDouble("Precio"));
                p.setStock(rs.getInt("Stock"));
                p.setImagen(rs.getString("Imagen"));
                p.setCategoria(rs.getString("Categoria"));
                p.setIdUsuario(rs.getInt("IdUsuario"));
                lista.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar Producto");
            e.printStackTrace();
        }
        return lista;
    }

    public boolean existeProducto(int idProducto) {
        String sql = "SELECT COUNT(*) FROM producto WHERE IdProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
