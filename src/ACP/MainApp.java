package ACP;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            String[] usuarios = {"Juan", "Pedro", "María", "Pepe"};
            Login loginWindow = new Login(usuarios);

            loginWindow.addLoginListener(e -> {
                String usuario = loginWindow.getUsuarioSeleccionado();

                if (usuario != null && !usuario.trim().isEmpty()) {
                    loginWindow.dispose(); // Cierra la ventana de login
                    Almacen.mostrar(); // Abre la app principal pasando el usuario
                } else {
                    JOptionPane.showMessageDialog(loginWindow, "Debes seleccionar un usuario.");
                }
            });

            loginWindow.setVisible(true);
        });
    }
}
