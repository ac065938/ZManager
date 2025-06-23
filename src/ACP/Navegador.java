package ACP;

import javax.swing.*;

public class Navegador {

    /**
     * Abre una nueva ventana y cierra la actual.
     * @param ventanaActual La ventana que se debe cerrar (normalmente "this").
     * @param claseVentana La clase de la nueva ventana que implementa el método estático "mostrar()".
     */
    public static void irA(JFrame ventanaActual, Class<?> claseVentana) {
        try {
            // Llama al método estático "mostrar" de la clase pasada como argumento
            claseVentana.getMethod("mostrar").invoke(null);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(ventanaActual,
                    "No se pudo abrir la ventana: " + claseVentana.getSimpleName(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // Cierra la ventana actual
        ventanaActual.dispose();
    }
}
