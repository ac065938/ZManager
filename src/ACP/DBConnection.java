package ACP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conexion;

    public static Connection conectar() {
        if (conexion == null) {
            try {
                // Cargar el driver de SQL Server
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

    public static Connection getConnection() {
        return conectar();
    }
}