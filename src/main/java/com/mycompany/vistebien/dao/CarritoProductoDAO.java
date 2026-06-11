package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.CarritoProducto;
import com.mycompany.vistebien.model.ItemCarrito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoProductoDAO {

    // ==========================================
// INSERTAR PRODUCTO AL CARRITO
// ==========================================
    public void insertarCarritoProducto(CarritoProducto cp) {

        try (Connection conn = ConexionBD.getConnection()) {

            int idCarrito = 0;

            // Buscar carrito pendiente
            String sqlBuscarCarrito
                    = "SELECT IdCarrito "
                    + "FROM carrito "
                    + "WHERE IdUsuario=? "
                    + "AND Estado='pendiente'";

            PreparedStatement stmtBuscar
                    = conn.prepareStatement(sqlBuscarCarrito);

            stmtBuscar.setInt(1, cp.getIdUsuario());

            ResultSet rsBuscar
                    = stmtBuscar.executeQuery();

            if (rsBuscar.next()) {

                idCarrito = rsBuscar.getInt("IdCarrito");

            } else {

                String sqlCrearCarrito
                        = "INSERT INTO carrito "
                        + "(IdUsuario, Estado) "
                        + "VALUES (?, 'pendiente')";

                PreparedStatement stmtCrear
                        = conn.prepareStatement(
                                sqlCrearCarrito,
                                Statement.RETURN_GENERATED_KEYS);

                stmtCrear.setInt(
                        1,
                        cp.getIdUsuario());

                stmtCrear.executeUpdate();

                ResultSet rsGenerado
                        = stmtCrear.getGeneratedKeys();

                if (rsGenerado.next()) {

                    idCarrito = rsGenerado.getInt(1);
                }
            }

            // ==========================
            // CONSULTAR STOCK
            // ==========================
            String sqlStock
                    = "SELECT Stock "
                    + "FROM producto "
                    + "WHERE IdProducto=?";

            PreparedStatement stmtStock
                    = conn.prepareStatement(sqlStock);

            stmtStock.setInt(
                    1,
                    cp.getIdProducto());

            ResultSet rsStock
                    = stmtStock.executeQuery();

            int stock = 0;

            if (rsStock.next()) {

                stock = rsStock.getInt("Stock");
            }

            if (stock <= 0) {

                System.out.println("Producto sin stock");
                return;
            }

            // ==========================
            // VERIFICAR SI YA EXISTE
            // ==========================
            String sqlExiste
                    = "SELECT Cantidad "
                    + "FROM carrito_producto "
                    + "WHERE IdCarrito=? "
                    + "AND IdProducto=? "
                    + "AND EstadoCompra='pendiente'";

            PreparedStatement stmtExiste
                    = conn.prepareStatement(sqlExiste);

            stmtExiste.setInt(1, idCarrito);
            stmtExiste.setInt(2, cp.getIdProducto());

            ResultSet rsExiste
                    = stmtExiste.executeQuery();

            if (rsExiste.next()) {

                int cantidadActual
                        = rsExiste.getInt("Cantidad");

                int nuevaCantidad
                        = cantidadActual + cp.getCantidad();

                if (nuevaCantidad > stock) {

                    nuevaCantidad = stock;
                }

                String sqlUpdate
                        = "UPDATE carrito_producto "
                        + "SET Cantidad=? "
                        + "WHERE IdCarrito=? "
                        + "AND IdProducto=?";

                PreparedStatement stmtUpdate
                        = conn.prepareStatement(sqlUpdate);

                stmtUpdate.setInt(
                        1,
                        nuevaCantidad);

                stmtUpdate.setInt(
                        2,
                        idCarrito);

                stmtUpdate.setInt(
                        3,
                        cp.getIdProducto());

                stmtUpdate.executeUpdate();

            } else {

                int cantidadInsertar = cp.getCantidad();

                // NO DEJAR INSERTAR MÁS DEL STOCK
                if (cantidadInsertar > stock) {

                    cantidadInsertar = stock;
                }

                String sqlInsert
                        = "INSERT INTO carrito_producto "
                        + "(IdCarrito, IdProducto, IdUsuario, Cantidad, EstadoCompra) "
                        + "VALUES (?, ?, ?, ?, 'pendiente')";

                PreparedStatement stmtInsert
                        = conn.prepareStatement(sqlInsert);

                stmtInsert.setInt(1, idCarrito);
                stmtInsert.setInt(2, cp.getIdProducto());
                stmtInsert.setInt(3, cp.getIdUsuario());
                stmtInsert.setInt(4, cantidadInsertar);

                stmtInsert.executeUpdate();
            }

            System.out.println("Producto agregado correctamente");

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // ==========================================
    // OBTENER ITEMS DEL CARRITO
    // ==========================================
    public List<ItemCarrito> obtenerItemsCarrito(
            int idUsuario) {

        List<ItemCarrito> lista
                = new ArrayList<>();

        String sql
                = "SELECT "
                + "p.IdProducto, "
                + "p.Nombre, "
                + "p.Precio, "
                + "p.Imagen, "
                + "cp.Cantidad "
                + "FROM carrito_producto cp "
                + "INNER JOIN producto p "
                + "ON cp.IdProducto = p.IdProducto "
                + "INNER JOIN carrito c "
                + "ON cp.IdCarrito = c.IdCarrito "
                + "WHERE cp.IdUsuario=? "
                + "AND cp.EstadoCompra='pendiente' "
                + "AND c.estado='pendiente'";

        try (
                Connection conn
                = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            ResultSet rs
                    = stmt.executeQuery();

            while (rs.next()) {

                ItemCarrito item
                        = new ItemCarrito();

                item.setIdProducto(
                        rs.getInt("IdProducto"));

                item.setNombreProducto(
                        rs.getString("Nombre"));

                item.setPrecio(
                        rs.getDouble("Precio"));

                item.setCantidad(
                        rs.getInt("Cantidad"));

                item.setImagen(
                        rs.getString("Imagen"));

                lista.add(item);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================================
    // ELIMINAR PRODUCTO
    // ==========================================
    public void eliminarProductoCarrito(
            int idUsuario,
            int idProducto) {

        String sql
                = "UPDATE carrito_producto "
                + "SET EstadoCompra='eliminada' "
                + "WHERE IdUsuario=? "
                + "AND IdProducto=?";

        try (
                Connection conn
                = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idProducto);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // ==========================================
    // CONTAR PRODUCTOS
    // ==========================================
    public int contarProductosCarrito(
            int idUsuario) {

        int cantidad = 0;

        String sql
                = "SELECT COALESCE(SUM(Cantidad),0) Total "
                + "FROM carrito_producto cp "
                + "INNER JOIN carrito c "
                + "ON cp.IdCarrito = c.IdCarrito "
                + "WHERE cp.IdUsuario=? "
                + "AND cp.EstadoCompra='pendiente' "
                + "AND c.estado='pendiente'";

        try (
                Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                cantidad = rs.getInt("Total");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        System.out.println(
                "CONTADOR CARRITO = " + cantidad);

        return cantidad;
    }

    // ==========================================
    // ELIMINAR DEFINITIVO
    // ==========================================
    public void eliminarCarritoProducto(
            int idCarrito,
            int idProducto) {

        String sql
                = "DELETE FROM carrito_producto "
                + "WHERE IdCarrito=? "
                + "AND IdProducto=?";

        try (
                Connection conn
                = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);
            stmt.setInt(2, idProducto);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

// ==========================================
// ACTUALIZAR CANTIDAD
// ==========================================
    public void actualizarCarritoProducto(
            int idCarrito,
            int idProducto,
            int cantidad) {

        try (
                Connection conn
                = ConexionBD.getConnection()) {

            // ==========================
            // CONSULTAR STOCK
            // ==========================
            String sqlStock
                    = "SELECT Stock "
                    + "FROM producto "
                    + "WHERE IdProducto=?";

            PreparedStatement stmtStock
                    = conn.prepareStatement(sqlStock);

            stmtStock.setInt(
                    1,
                    idProducto);

            ResultSet rsStock
                    = stmtStock.executeQuery();

            int stock = 0;

            if (rsStock.next()) {

                stock = rsStock.getInt("Stock");
            }

            // ==========================
            // VALIDAR CANTIDAD
            // ==========================
            if (cantidad > stock) {

                cantidad = stock;
            }

            if (cantidad < 1) {

                cantidad = 1;
            }

            // ==========================
            // ACTUALIZAR
            // ==========================
            String sql
                    = "UPDATE carrito_producto "
                    + "SET Cantidad=? "
                    + "WHERE IdCarrito=? "
                    + "AND IdProducto=?";

            PreparedStatement stmt
                    = conn.prepareStatement(sql);

            stmt.setInt(1, cantidad);
            stmt.setInt(2, idCarrito);
            stmt.setInt(3, idProducto);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public List<CarritoProducto> listarCarritoProductos() {

        List<CarritoProducto> lista
                = new ArrayList<>();

        String sql
                = "SELECT * FROM carrito_producto";

        try (
                Connection conn
                = ConexionBD.getConnection(); Statement stmt
                = conn.createStatement(); ResultSet rs
                = stmt.executeQuery(sql)) {

            while (rs.next()) {

                CarritoProducto cp
                        = new CarritoProducto();

                cp.setIdCarrito(
                        rs.getInt("IdCarrito"));

                cp.setIdProducto(
                        rs.getInt("IdProducto"));

                cp.setIdUsuario(
                        rs.getInt("IdUsuario"));

                cp.setCantidad(
                        rs.getInt("Cantidad"));

                cp.setEstadoCompra(
                        rs.getString("EstadoCompra"));

                lista.add(cp);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    public List<CarritoProducto> buscarCarritoProductos(
            String filtro) {

        List<CarritoProducto> lista
                = new ArrayList<>();

        String sql
                = "SELECT * "
                + "FROM carrito_producto "
                + "WHERE CAST(IdProducto AS CHAR) LIKE ? "
                + "OR CAST(IdUsuario AS CHAR) LIKE ?";

        try (
                Connection conn
                = ConexionBD.getConnection(); PreparedStatement stmt
                = conn.prepareStatement(sql)) {

            String like
                    = "%" + filtro + "%";

            stmt.setString(1, like);
            stmt.setString(2, like);

            ResultSet rs
                    = stmt.executeQuery();

            while (rs.next()) {

                CarritoProducto cp
                        = new CarritoProducto();

                cp.setIdCarrito(
                        rs.getInt("IdCarrito"));

                cp.setIdProducto(
                        rs.getInt("IdProducto"));

                cp.setIdUsuario(
                        rs.getInt("IdUsuario"));

                cp.setCantidad(
                        rs.getInt("Cantidad"));

                cp.setEstadoCompra(
                        rs.getString("EstadoCompra"));

                lista.add(cp);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // ==========================================
    // ACTUALIZAR ESTADO DE TODOS LOS PRODUCTOS
    // DE UN CARRITO (para finalizar compra)
    // ==========================================
    /**
     * Updates EstadoCompra for every row belonging to a given cart. Called by
     * CarritoController.finalizarCompra().
     */
    public void actualizarEstadoCompraCarrito(int idCarrito, String nuevoEstado) {
        String sql = "UPDATE carrito_producto SET EstadoCompra = ? WHERE IdCarrito = ?";
        try (Connection conn = ConexionBD.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, idCarrito);
            stmt.executeUpdate();
            System.out.println("EstadoCompra actualizado a '" + nuevoEstado
                    + "' para todos los productos del carrito " + idCarrito);
        } catch (SQLException e) {
            System.out.println("Error al actualizar EstadoCompra del carrito");
            e.printStackTrace();
        }
    }

}
