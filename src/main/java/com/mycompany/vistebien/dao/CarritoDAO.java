package com.mycompany.vistebien.dao;

import com.mycompany.vistebien.model.Carrito;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarritoDAO {

    // =====================================
    // INSERTAR CARRITO
    // =====================================
    public void insertarCarrito(Carrito carrito) {

        String sql =
                "INSERT INTO carrito (IdUsuario, estado) "
                + "VALUES (?, ?)";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carrito.getIdUsuario());
            stmt.setString(2, carrito.getEstado());

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================
    // LISTAR
    // =====================================
    public List<Carrito> listarCarritos() {

        List<Carrito> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM carrito";

        try (
                Connection conn = ConexionBD.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Carrito c = new Carrito();

                c.setIdCarrito(
                        rs.getInt("IdCarrito"));

                c.setIdUsuario(
                        rs.getInt("IdUsuario"));

                c.setEstado(
                        rs.getString("estado"));

                lista.add(c);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // =====================================
    // ELIMINAR
    // =====================================
    public void eliminarCarrito(int idCarrito) {

        String sql =
                "DELETE FROM carrito "
                + "WHERE IdCarrito=?";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idCarrito);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // =====================================
    // BUSCAR
    // =====================================
    public List<Carrito> buscarCarrito(String filtro) {

        List<Carrito> lista = new ArrayList<>();

        String sql =
                "SELECT * FROM carrito "
                + "WHERE CAST(IdCarrito AS CHAR) LIKE ? "
                + "OR CAST(IdUsuario AS CHAR) LIKE ? "
                + "OR estado LIKE ?";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            String like =
                    "%" + filtro + "%";

            stmt.setString(1, like);
            stmt.setString(2, like);
            stmt.setString(3, like);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Carrito c = new Carrito();

                c.setIdCarrito(
                        rs.getInt("IdCarrito"));

                c.setIdUsuario(
                        rs.getInt("IdUsuario"));

                c.setEstado(
                        rs.getString("estado"));

                lista.add(c);
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return lista;
    }

    // =====================================
    // OBTENER CARRITO PENDIENTE
    // =====================================
    public Carrito obtenerCarritoPendiente(
            int idUsuario) {

        String sql =
                "SELECT * "
                + "FROM carrito "
                + "WHERE IdUsuario=? "
                + "AND estado='pendiente'";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                Carrito carrito =
                        new Carrito();

                carrito.setIdCarrito(
                        rs.getInt("IdCarrito"));

                carrito.setIdUsuario(
                        rs.getInt("IdUsuario"));

                carrito.setEstado(
                        rs.getString("estado"));

                return carrito;
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }

        return null;
    }

    // =====================================
    // CREAR CARRITO PENDIENTE
    // =====================================
    public int crearCarritoPendiente(
            int idUsuario) {

        String sql =
                "INSERT INTO carrito "
                + "(IdUsuario, estado) "
                + "VALUES (?, 'pendiente')";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(
                                sql,
                                Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, idUsuario);

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

    // =====================================
    // CAMBIAR ESTADO
    // =====================================
    public void actualizarEstado(
            int idCarrito,
            String estado) {

        String sql =
                "UPDATE carrito "
                + "SET estado=? "
                + "WHERE IdCarrito=?";

        try (
                Connection conn = ConexionBD.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)) {

            stmt.setString(1, estado);
            stmt.setInt(2, idCarrito);

            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}