package com.mycompany.vistebien.dao;

import java.sql.*;

public class DetalleCompraDAO {

    public void insertarDetalle(
            int idCompra,
            int idProducto,
            int cantidad,
            double precio,
            double subtotal) {

        String sql =
                "INSERT INTO detalle_compra "
                + "(IdCompra, IdProducto, Cantidad, PrecioUnitario, Subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)) {

            stmt.setInt(1, idCompra);
            stmt.setInt(2, idProducto);
            stmt.setInt(3, cantidad);
            stmt.setDouble(4, precio);
            stmt.setDouble(5, subtotal);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}