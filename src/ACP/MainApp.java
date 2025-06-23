package ACP;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginWindow = new Login();

            loginWindow.addLoginListener(e -> {
                String usuario = loginWindow.getUsuarioSeleccionado();

                if (usuario != null && !usuario.trim().isEmpty()) {
                    loginWindow.dispose();
                    Almacen.mostrar();
                } else {
                    JOptionPane.showMessageDialog(loginWindow, "Debes seleccionar un usuario.");
                }
                
            });

            loginWindow.setVisible(true);
        });
    }
}
