package com.mycompany.vistebien.tests;

import com.mycompany.vistebien.dao.ProductoDAO;
import com.mycompany.vistebien.model.Producto;
import com.mycompany.vistebien.dao.ConexionBD;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoDAOTest {

    private ProductoDAO dao;

    @BeforeEach
    void setUp() {
        dao = new ProductoDAO();

        try (Connection conn = ConexionBD.getConnection(); Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DELETE FROM producto WHERE Nombre='Camisa' AND Categoria='Ropa'");

            // Asegura que exista el administrador
            stmt.executeUpdate(
                    "INSERT INTO administrador (IdAdministrador, Nombre, Correo, Contrasena, Telefono) "
                    + "VALUES (1, 'AdminTest', 'admin@test.com', '1234', '3001234567') "
                    + "ON DUPLICATE KEY UPDATE Nombre='AdminTest'"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testInsertarProductoValido() {
        Producto p = new Producto("Camisa", "Algodón", 35000, 10, "camisa.jpg", "Ropa", 1);
        boolean resultado = dao.insertarProducto(p);
        assertTrue(resultado, "El producto válido debería insertarse correctamente");
    }

    @Test
    void testInsertarProductoSinNombre() {
        Producto p = new Producto("", "Algodón", 35000, 5, "camisa.jpg", "Ropa", 1);
        boolean resultado = dao.insertarProducto(p);
        assertFalse(resultado, "No debería insertarse un producto sin nombre");
    }

    @Test
    void testInsertarProductoPrecioNegativo() {
        Producto p = new Producto("Camisa", "Algodón", -1000, 5, "camisa.jpg", "Ropa", 1);
        boolean resultado = dao.insertarProducto(p);
        assertFalse(resultado, "No debería insertarse un producto con precio negativo");
    }
}
