package ACP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;

    public static Connection conectar() {
        try {
            // Si no existe o está cerrada, se crea una nueva
            if (conexion == null || conexion.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

                String servidor = "ING\\SQLEXPRESS01"; 
                String puerto = "1433";
                String baseDeDatos = "bd_prueba";
                String usuario = "sa";         
                String contrasena = "ACP1!"; 

                String connectionUrl = "jdbc:sqlserver://" + servidor + ":" + puerto + ";" +
                        "databaseName=" + baseDeDatos + ";" +
                        "encrypt=false;" +
                        "trustServerCertificate=true;" +
                        "user=" + usuario + ";" +
                        "password=" + contrasena + ";";

                conexion = DriverManager.getConnection(connectionUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            conexion = null;
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

    public static Connection getConnection() {
        return conectar();
    }
}
