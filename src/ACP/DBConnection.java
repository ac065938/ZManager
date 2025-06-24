package ACP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;

    private static final String URL = "jdbc:mysql://localhost:3306/tu_basededatos";
    private static final String USER = "tu_usuario";
    private static final String PASSWORD = "tu_contraseña";

    public static Connection getConnection() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            conectar();
        }
        return conexion;
    }

    public static boolean estaConectado() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    public static boolean conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            conexion = null;
            return false;
        }
    }

    public static void cerrar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
