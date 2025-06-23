package ACP;

import javax.swing.*;
import java.awt.*;

public class Administracion extends JFrame {

    public Administracion() {
        setTitle("Zaragoza Marine Manager - Administración");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        // Carga la imagen desde el classpath
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));
        // Fija el icono de la ventana
        setIconImage(icon);

        // ---------- Menú tipo hamburguesa ----------
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JButton opcionesButton = new JButton("");
        opcionesButton.setPreferredSize(new Dimension(10, 20));
        opcionesButton.setIcon(new ImageIcon(Administracion.class.getResource("/menu.png")));
        menuBar.add(opcionesButton);

        JPopupMenu opcionesMenu = new JPopupMenu();

        JMenuItem irVentas = new JMenuItem("Ir a Ventas");
        irVentas.addActionListener(e -> Navegador.irA(this, Ventas.class));
        opcionesMenu.add(irVentas);

        JMenuItem irAlmacen = new JMenuItem("Ir a Almacén");
        irAlmacen.addActionListener(e -> Navegador.irA(this, Almacen.class));
        opcionesMenu.add(irAlmacen);

        JMenuItem seleccionarBD = new JMenuItem("Seleccionar base de datos");
        seleccionarBD.addActionListener(e -> SelectorBaseDatos.mostrarDialogo(this));
        opcionesMenu.add(seleccionarBD);

        JMenuItem cerrarSesion = new JMenuItem("Cerrar sesión");
        cerrarSesion.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro que deseas cerrar sesión?",
                "Confirmar cierre",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        opcionesMenu.addSeparator();
        opcionesMenu.add(cerrarSesion);

        opcionesButton.addActionListener(e -> opcionesMenu.show(opcionesButton, 0, opcionesButton.getHeight()));

        // ---------- Barra tipo ribbon ----------
        JToolBar ribbon = new JToolBar();
        ribbon.setFloatable(false);
        ribbon.setLayout(new FlowLayout(FlowLayout.LEFT));
        ribbon.setBackground(Color.WHITE);

        // ---------- Panel de acciones ----------
        JPanel accionesPanel = new JPanel(new FlowLayout());
        accionesPanel.setBackground(Color.WHITE);
        accionesPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton btnPerfiles = new JButton("Perfiles de usuario");
        btnPerfiles.setIcon(new ImageIcon(Administracion.class.getResource("/icono_perfiles.png")));
        btnPerfiles.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPerfiles.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPerfiles.setBackground(Color.WHITE);
        btnPerfiles.addActionListener(e -> Perfiles.mostrar(this));

        JButton btnConexiones = new JButton("Conexiones de BD");
        btnConexiones.setIcon(new ImageIcon(Administracion.class.getResource("/icono_conexiones.png")));
        btnConexiones.setHorizontalTextPosition(SwingConstants.CENTER);
        btnConexiones.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnConexiones.setBackground(Color.WHITE);
        btnConexiones.addActionListener(e -> JOptionPane.showMessageDialog(this, "Abrir conexiones de base de datos"));

        accionesPanel.add(btnPerfiles);
        accionesPanel.add(btnConexiones);

        ribbon.add(accionesPanel);
        getContentPane().add(ribbon, BorderLayout.NORTH);
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            Administracion ventana = new Administracion();
            ventana.setVisible(true);
        });
    }
}