package ACP;

import javax.swing.*;

public class SelectorBaseDatos {

    // Variable global para guardar la selección actual
    private static String baseDeDatosActual = "Producción";

    public static void mostrarDialogo(JFrame parent) {
        String[] opciones = {"Producción", "Desarrollo", "Pruebas", "Remota"};
        String seleccion = (String) JOptionPane.showInputDialog(
            parent,
            "Selecciona la base de datos:",
            "Conexión a Base de Datos",
            JOptionPane.PLAIN_MESSAGE,
            null,
            opciones,
            baseDeDatosActual
        );

        if (seleccion != null) {
            baseDeDatosActual = seleccion;
            JOptionPane.showMessageDialog(parent, "Conectado a: " + baseDeDatosActual);

        }
    }

    public static String getBaseDeDatosActual() {
        return baseDeDatosActual;
    }
}
