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

        // Icono de ventana
        Image icon = Toolkit.getDefaultToolkit()
                            .getImage(getClass().getResource("/LOGOZM.png"));
        setIconImage(icon);

        // -------------------- MENÚ SUPERIOR --------------------
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        // Botón: Ventas
        JButton ventasBtn = new JButton(new ImageIcon(getClass().getResource("/ventas.png")));
        ventasBtn.setPreferredSize(new Dimension(20, 45));
        ventasBtn.setToolTipText("Ir a Ventas");
        ventasBtn.addActionListener(e -> Navegador.irA(this, Ventas.class));
        menuBar.add(ventasBtn);

        // Botón: Almacén
        JButton almacenBtn = new JButton(new ImageIcon(getClass().getResource("/almacen.png")));
        almacenBtn.setPreferredSize(new Dimension(20, 20));
        almacenBtn.setToolTipText("Ir a Almacén");
        almacenBtn.addActionListener(e -> Navegador.irA(this, Almacen.class));
        menuBar.add(almacenBtn);

        // Botón: Base de datos
        JButton configBtn = new JButton(new ImageIcon(getClass().getResource("/BD.png")));
        configBtn.setPreferredSize(new Dimension(20, 20));
        configBtn.setToolTipText("Seleccionar base de datos");

        JPopupMenu menuConfig = new JPopupMenu();
        JMenuItem seleccionarBD = new JMenuItem("Seleccionar base de datos");
        seleccionarBD.addActionListener(e -> SelectorBaseDatos.mostrarDialogo(this));
        menuConfig.add(seleccionarBD);
        configBtn.addActionListener(e -> menuConfig.show(configBtn, 0, configBtn.getHeight()));
        menuBar.add(configBtn);

        // Botón: Cerrar sesión
        JButton cerrarSesionBtn = new JButton(new ImageIcon(getClass().getResource("/cerrar.png")));
        cerrarSesionBtn.setPreferredSize(new Dimension(20, 20));
        cerrarSesionBtn.setToolTipText("Cerrar sesión");
        cerrarSesionBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro que deseas cerrar sesión?", "Confirmar cierre", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        menuBar.add(cerrarSesionBtn);

        // -------------------- RIBBON --------------------
        JToolBar ribbon = new JToolBar();
        ribbon.setFloatable(false);
        ribbon.setLayout(new FlowLayout(FlowLayout.LEFT));
        ribbon.setBackground(Color.WHITE);

        // -------------------- ACCIONES --------------------
        JPanel accionesPanel = new JPanel(new FlowLayout());
        accionesPanel.setBackground(Color.WHITE);
        accionesPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton btnPerfiles = new JButton("Perfiles de usuario");
        btnPerfiles.setIcon(new ImageIcon(getClass().getResource("/icono_perfiles.png")));
        btnPerfiles.setHorizontalTextPosition(SwingConstants.CENTER);
        btnPerfiles.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnPerfiles.setBackground(Color.WHITE);
        btnPerfiles.addActionListener(e -> Perfiles.mostrar(this));

        JButton btnConexiones = new JButton("Conexiones de BD");
        btnConexiones.setIcon(new ImageIcon(getClass().getResource("/icono_conexiones.png")));
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
