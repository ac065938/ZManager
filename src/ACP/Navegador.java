package ACP;

import javax.swing.*;

public class Navegador {

    public static void irA(JFrame ventanaActual, Class<?> claseVentana) {
        try {
            claseVentana.getMethod("mostrar").invoke(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ventanaActual,
                    "No se pudo abrir la ventana: " + claseVentana.getSimpleName(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        ventanaActual.dispose();
    }
}
