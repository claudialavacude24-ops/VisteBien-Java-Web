package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.CarritoProducto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoProductoDAO {

    // Insertar registro: crea carrito si no existe y luego agrega producto
    public void insertarCarritoProducto(CarritoProducto cp) {
        try (Connection conn = ConexionBD.getConnection()) {
            int idCarrito = 0;

            // 1. Verificar si el usuario ya tiene un carrito
            String sqlBuscar = "SELECT IdCarrito FROM carrito WHERE IdUsuario=?";
            PreparedStatement stmtBuscar = conn.prepareStatement(sqlBuscar);
            stmtBuscar.setInt(1, cp.getIdUsuario());
            ResultSet rsBuscar = stmtBuscar.executeQuery();

            if (rsBuscar.next()) {
                idCarrito = rsBuscar.getInt("IdCarrito");
            } else {
                // 2. Si no existe, crear uno nuevo
                String sqlCarrito = "INSERT INTO carrito (IdUsuario) VALUES (?)";
                PreparedStatement stmtCarrito = conn.prepareStatement(sqlCarrito, Statement.RETURN_GENERATED_KEYS);
                stmtCarrito.setInt(1, cp.getIdUsuario());
                stmtCarrito.executeUpdate();

                ResultSet rs = stmtCarrito.getGeneratedKeys();
                if (rs.next()) {
                    idCarrito = rs.getInt(1);
                }
            }

            // 3. Insertar producto en el carrito
            String sqlCP = "INSERT INTO carrito_producto (IdCarrito, IdProducto, IdUsuario, Cantidad) VALUES (?, ?, ?, ?)";
            PreparedStatement stmtCP = conn.prepareStatement(sqlCP);
            stmtCP.setInt(1, idCarrito);
            stmtCP.setInt(2, cp.getIdProducto());
            stmtCP.setInt(3, cp.getIdUsuario());
            stmtCP.setInt(4, cp.getCantidad());
            stmtCP.executeUpdate();

            cp.setIdCarrito(idCarrito);

            System.out.println("Producto agregado al carrito " + idCarrito);
        } catch (SQLException e) {
            System.out.println("Error al insertar CarritoProducto");
            e.printStackTrace();
        }
    }

    // Listar registros
    public List<CarritoProducto> listarCarritoProductos() {
        List<CarritoProducto> lista = new ArrayList<>();
        String sql = "SELECT IdCarrito, IdProducto, IdUsuario, Cantidad FROM carrito_producto";

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CarritoProducto cp = new CarritoProducto();
                cp.setIdCarrito(rs.getInt("IdCarrito"));
                cp.setIdProducto(rs.getInt("IdProducto"));
                cp.setIdUsuario(rs.getInt("IdUsuario"));
                cp.setCantidad(rs.getInt("Cantidad"));
                lista.add(cp);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar CarritoProducto");
            e.printStackTrace();
        }
        return lista;
    }

    // Eliminar registro (por IdCarrito e IdProducto)
    public void eliminarCarritoProducto(int idCarrito, int idProducto) {
        String sql = "DELETE FROM carrito_producto WHERE IdCarrito=? AND IdProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);
            stmt.setInt(2, idProducto);
            stmt.executeUpdate();

            System.out.println("CarritoProducto eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar CarritoProducto");
            e.printStackTrace();
        }
    }

    // Actualizar cantidad de un producto en el carrito
    public void actualizarCarritoProducto(int idCarrito, int idProducto, int nuevaCantidad) {
        String sql = "UPDATE carrito_producto SET Cantidad=? WHERE IdCarrito=? AND IdProducto=?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nuevaCantidad);
            stmt.setInt(2, idCarrito);
            stmt.setInt(3, idProducto);
            int filas = stmt.executeUpdate();

            if (filas > 0) {
                System.out.println("Cantidad actualizada correctamente en el carrito");
            } else {
                System.out.println("No se encontró el producto en el carrito para actualizar");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar CarritoProducto");
            e.printStackTrace();
        }
    }

    // Buscar registros por IdProducto o IdUsuario
    public List<CarritoProducto> buscarCarritoProductos(String filtro) {
        List<CarritoProducto> lista = new ArrayList<>();
        String sql = "SELECT IdCarrito, IdProducto, IdUsuario, Cantidad "
                + "FROM carrito_producto "
                + "WHERE CAST(IdProducto AS CHAR) LIKE ? OR CAST(IdUsuario AS CHAR) LIKE ?";

        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeFiltro = "%" + filtro + "%";
            stmt.setString(1, likeFiltro);
            stmt.setString(2, likeFiltro);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                CarritoProducto cp = new CarritoProducto();
                cp.setIdCarrito(rs.getInt("IdCarrito"));
                cp.setIdProducto(rs.getInt("IdProducto"));
                cp.setIdUsuario(rs.getInt("IdUsuario"));
                cp.setCantidad(rs.getInt("Cantidad"));
                lista.add(cp);
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar CarritoProducto");
            e.printStackTrace();
        }
        return lista;
    }
}
