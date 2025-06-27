package ACP;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> mostrarLogin());
    }

    public static void mostrarLogin() {
        Login loginWindow = new Login();
        loginWindow.setVisible(true);
    }
}
