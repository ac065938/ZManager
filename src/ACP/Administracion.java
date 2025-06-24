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
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        Dimension buttonSize = new Dimension(80, 60);

        JButton ventasBtn = new JButton("Ventas", new ImageIcon(getClass().getResource("/ventas.png")));
        ventasBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        ventasBtn.setPreferredSize(new Dimension(80, 73));
        ventasBtn.setToolTipText("Ir a Ventas");
        ventasBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        ventasBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        ventasBtn.setBackground(Color.WHITE);
        ventasBtn.addActionListener(e -> Navegador.irA(this, Ventas.class));
        menuBar.add(ventasBtn);

        JButton adminBtn = new JButton("Almacen", new ImageIcon(getClass().getResource("/almacen.png")));
        adminBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        adminBtn.setPreferredSize(new Dimension(80, 73));
        adminBtn.setToolTipText("Ir a Almacen");
        adminBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        adminBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        adminBtn.setBackground(Color.WHITE);
        adminBtn.addActionListener(e -> Navegador.irA(this, Almacen.class));
        menuBar.add(adminBtn);

        JButton configBtn = new JButton("BD", new ImageIcon(getClass().getResource("/BD.png")));
        configBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        configBtn.setPreferredSize(new Dimension(80, 73));
        configBtn.setToolTipText("Seleccionar Base de Datos");
        configBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        configBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        configBtn.setBackground(Color.WHITE);

        JPopupMenu menuConfig = new JPopupMenu();
        JMenuItem seleccionarBD = new JMenuItem("Seleccionar base de datos");
        seleccionarBD.addActionListener(e -> SelectorBaseDatos.mostrarDialogo(this));
        menuConfig.add(seleccionarBD);
        configBtn.addActionListener(e -> menuConfig.show(configBtn, 0, configBtn.getHeight()));
        menuBar.add(configBtn);

        JButton cerrarSesionBtn = new JButton("Cerrar", new ImageIcon(getClass().getResource("/cerrar.png")));
        cerrarSesionBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        cerrarSesionBtn.setPreferredSize(new Dimension(80, 73));
        cerrarSesionBtn.setToolTipText("Cerrar sesión");
        cerrarSesionBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        cerrarSesionBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        cerrarSesionBtn.setBackground(Color.WHITE);
        cerrarSesionBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro que deseas cerrar sesión?",
                "Confirmar cierre",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                this.dispose();
                MainApp.mostrarLogin();
            }
        });
        menuBar.add(cerrarSesionBtn);

        // -------------------- CONEXIÓN BD --------------------
        JLabel estadoBD = new JLabel();
        estadoBD.setFont(new Font("Segoe UI", Font.BOLD, 12));
        estadoBD.setOpaque(true);
        estadoBD.setHorizontalAlignment(SwingConstants.CENTER);

        if (DBConnection.estaConectado()) {
            estadoBD.setText("Conectado a BD");
            estadoBD.setBackground(new Color(0, 153, 51));
            estadoBD.setForeground(Color.WHITE);
        } else {
            estadoBD.setText("Sin conexión a BD");
            estadoBD.setBackground(new Color(204, 0, 0));
            estadoBD.setForeground(Color.WHITE);
        }

        getContentPane().add(estadoBD, BorderLayout.SOUTH);

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
        btnConexiones.addActionListener(e -> {
            ConexionesBD conexionesBDFrame = new ConexionesBD(); 
            conexionesBDFrame.setVisible(true);
        });

        accionesPanel.add(btnPerfiles);
        accionesPanel.add(btnConexiones);

        // -------------------- RIBBON --------------------
        JToolBar ribbon = new JToolBar();
        ribbon.setFloatable(false);
        ribbon.setLayout(new FlowLayout(FlowLayout.LEFT));
        ribbon.setBackground(Color.WHITE);
        ribbon.add(accionesPanel);

        // -------------------- CONTENEDOR SUPERIOR --------------------
        JPanel contenedorSuperior = new JPanel();
        contenedorSuperior.setLayout(new BoxLayout(contenedorSuperior, BoxLayout.Y_AXIS));

        JPanel panelReloj = new JPanel(new BorderLayout());
        panelReloj.setBackground(Color.WHITE);
        panelReloj.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        RelojMexico reloj = new RelojMexico();
        reloj.setText("Hora: 12:22:16");
        reloj.setForeground(new Color(0, 0, 0));
        reloj.setFont(new Font("Segoe UI", Font.BOLD, 14));
        reloj.setHorizontalAlignment(SwingConstants.LEFT);
        panelReloj.add(reloj, BorderLayout.WEST);

        contenedorSuperior.add(panelReloj);
        contenedorSuperior.add(ribbon);

        getContentPane().add(contenedorSuperior, BorderLayout.NORTH);
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            Administracion ventana = new Administracion();
            ventana.setVisible(true);
        });
    }
}
