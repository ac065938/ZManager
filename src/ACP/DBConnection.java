package ACP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mysql://localhost:3306/tu_basededatos";
        String user = "tu_usuario";
        String password = "tu_contraseña";
        return DriverManager.getConnection(url, user, password);
    }
    
    public static boolean estaConectado() {
        try (Connection conn = DBConnection.getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}
