
package ACP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;

    public static Connection conectar() {
        if (conexion == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexion = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bd_prueba?useSSL=false&serverTimezone=America/Mexico_City", // ⚠️ Cambia a tu nombre de BD real
                    "root", // ⚠️ Cambia al usuario que estás usando
                    "" // ⚠️ Cambia tu contraseña
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return conexion;
    }

    public static boolean estaConectado() {
        try {
            return conectar() != null && !conectar().isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}

