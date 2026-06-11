package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Compra;
import java.sql.*;

public class CompraDAO {

    public int insertarCompra(Compra compra) {

        String sql =
                "INSERT INTO compra "
                + "(IdUsuario, IdCarrito, IdMetodo, Total, Estado) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, compra.getIdUsuario());
            stmt.setInt(2, compra.getIdCarrito());
            stmt.setInt(3, compra.getIdMetodo());
            stmt.setDouble(4, compra.getTotal());
            stmt.setString(5, compra.getEstado());

            stmt.executeUpdate();

            ResultSet rs =
                    stmt.getGeneratedKeys();

            if (rs.next()) {

                return rs.getInt(1);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return 0;
    }
}