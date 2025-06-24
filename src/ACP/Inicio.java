package ACP;

import javax.swing.*;
import java.awt.*;

public class Inicio extends JDialog {

    public Inicio(JFrame parent) {
        super(parent, "Selecciona una sección", true);
        setSize(480, 280);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Icono principal
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png")));

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel lbl = new JLabel("¿A qué sección deseas entrar?");
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(lbl, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;

        JButton btnAlmacen = crearBoton("Almacén", "/almacen.png");
        btnAlmacen.addActionListener(e -> {
            dispose();
            Almacen.mostrar();
        });

        JButton btnVentas = crearBoton("Ventas", "/ventas.png");
        btnVentas.addActionListener(e -> {
            dispose();
            Ventas.mostrar();
        });

        JButton btnAdmin = crearBoton("Administración", "/admin.png");
        btnAdmin.addActionListener(e -> {
            dispose();
            Administracion.mostrar();
        });

        gbc.gridx = 0;
        panel.add(btnAlmacen, gbc);
        gbc.gridx = 1;
        panel.add(btnVentas, gbc);
        gbc.gridx = 2;
        panel.add(btnAdmin, gbc);

        getContentPane().add(panel);
    }

    private JButton crearBoton(String texto, String rutaIcono) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        boton.setVerticalTextPosition(SwingConstants.BOTTOM);
        boton.setHorizontalTextPosition(SwingConstants.CENTER);
        boton.setFocusPainted(false);
        boton.setBackground(new Color(240, 240, 240));

        // Carga el ícono
        try {
            ImageIcon icono = new ImageIcon(getClass().getResource(rutaIcono));
            Image scaled = icono.getImage().getScaledInstance(48, 48, Image.SCALE_SMOOTH);
            boton.setIcon(new ImageIcon(scaled));
        } catch (Exception e) {
            System.err.println("No se encontró el icono: " + rutaIcono);
        }

        return boton;
    }

    public static void mostrar(JFrame parent) {
        Inicio dialog = new Inicio(parent);
        dialog.setVisible(true);
    }
}
