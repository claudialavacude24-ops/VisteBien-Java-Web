package com.mycompany.vistebien.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String URL = "jdbc:mysql://localhost:3306/vistebien?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // pon tu contraseña real aquí

    public static Connection getConnection() {
        try {
            // Cargar el driver explícitamente
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("Conexión exitosa a la base de datos VISTEBIEN");
            }

            return conn;

        } catch (ClassNotFoundException e) {
            System.out.println("Driver JDBC de MySQL no encontrado");
            e.printStackTrace();
            return null;
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
            return null;
        }
    }
}
