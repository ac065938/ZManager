package ACP;

import ACP.Ventas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.URL;
import java.sql.SQLException;

public class Ventas extends JFrame {

    private JLabel estado;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    public Ventas() {
        setTitle("ZManager 2.0 - Ventas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        // Carga_la_imagen_desde_el_classpath
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));
        // Fija_el_icono_de_la_ventana
        setIconImage(icon);
       
        //Menú_principal
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        // Estilo_común
        Dimension buttonSize = new Dimension(80, 60);

        // -------------------- BOTÓN: VENTAS --------------------
        JButton Almacenbtn = new JButton("Almacen", new ImageIcon(getClass().getResource("/almacen.png")));
        Almacenbtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        Almacenbtn.setPreferredSize(new Dimension(80, 73));
        Almacenbtn.setToolTipText("Ir a Almacen");
        Almacenbtn.setHorizontalTextPosition(SwingConstants.CENTER);
        Almacenbtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        Almacenbtn.setBackground(Color.WHITE);
        Almacenbtn.addActionListener(e -> Navegador.irA(this, Almacen.class));
        menuBar.add(Almacenbtn);

        // -------------------- BOTÓN: ADMINISTRACIÓN --------------------
        JButton adminBtn = new JButton("Administración", new ImageIcon(getClass().getResource("/admin.png")));
        adminBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        adminBtn.setPreferredSize(new Dimension(80, 73));
        adminBtn.setToolTipText("Ir a Administración");
        adminBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        adminBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        adminBtn.setBackground(Color.WHITE);
        adminBtn.addActionListener(e -> Navegador.irA(this, Administracion.class));
        menuBar.add(adminBtn);

        // -------------------- BOTÓN: BASE DE DATOS --------------------
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

        //BOTON CERRAR SESION - REINGRESO
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
                this.dispose(); // Cierra la ventana actual
                MainApp.mostrarLogin(); // Vuelve a ejecutar el_login_con_listener
            }
        });
        menuBar.add(cerrarSesionBtn);

        //---------- Barra_tipo_Ribbon ----------
        JToolBar ribbon = new JToolBar();
        ribbon.setBackground(new Color(255, 255, 255));
        ribbon.setFloatable(false);
        ribbon.setLayout(new FlowLayout(FlowLayout.LEFT));

        //---------- Buscar_Producto ----------
        JPanel clienteProductoPanel = new JPanel(new BorderLayout());
        clienteProductoPanel.setBackground(new Color(255, 255, 255));
        clienteProductoPanel.setPreferredSize(new Dimension(210, 105));
        clienteProductoPanel.setBorder(BorderFactory.createTitledBorder("Buscar Producto"));

        JPanel filaSKU = new JPanel(new GridLayout(1, 2, 5, 0));

        JTextField campoSKU = new JTextField("%SKU%");
        campoSKU.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoSKU.getText().equals("%SKU%")) campoSKU.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoSKU.getText().trim().isEmpty()) campoSKU.setText("%SKU%");
            }
        });

        JTextField campoFiltro = new JTextField("%Filtro%");
        campoFiltro.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoFiltro.getText().equals("%Filtro%")) campoFiltro.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoFiltro.getText().trim().isEmpty()) campoFiltro.setText("%Filtro%");
            }
        });

        filaSKU.add(campoSKU);
        filaSKU.add(campoFiltro);

        JTextField campoCaracteristicas2 = new JTextField("Caracteristicas");
        campoCaracteristicas2.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoCaracteristicas2.getText().equals("Caracteristicas")) campoCaracteristicas2.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoCaracteristicas2.getText().trim().isEmpty()) campoCaracteristicas2.setText("Caracteristicas");
            }
        });

        clienteProductoPanel.add(filaSKU, BorderLayout.NORTH);
        clienteProductoPanel.add(campoCaracteristicas2, BorderLayout.CENTER);

        ribbon.add(clienteProductoPanel);

        JCheckBox chckbxNewCheckBox = new JCheckBox("Limpiar antes de cargar información");
        chckbxNewCheckBox.setBackground(new Color(255, 255, 255));
        chckbxNewCheckBox.setSelected(true);
        clienteProductoPanel.add(chckbxNewCheckBox, BorderLayout.SOUTH);

        JPanel filtrosPanel = new JPanel(new GridLayout(2, 1));
        filtrosPanel.setBackground(new Color(255, 255, 255));
        filtrosPanel.setPreferredSize(new Dimension(175, 105));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        JCheckBox cbStock = new JCheckBox("Solo en existencia", false);
        cbStock.setBackground(new Color(255, 255, 255));
        JCheckBox cbDescuentos = new JCheckBox("Mostrar con descuento");
        cbDescuentos.setBackground(new Color(255, 255, 255));

        filtrosPanel.add(cbStock);
        filtrosPanel.add(cbDescuentos);

        ribbon.add(filtrosPanel);

        JPanel accionesPanel = new JPanel(new FlowLayout());
        accionesPanel.setBackground(new Color(255, 255, 255));
        accionesPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton btnCotizar = new JButton("Registrar Cliente");
        btnCotizar.setBackground(new Color(255, 255, 255));
        btnCotizar.setIcon(new ImageIcon(Ventas.class.getResource("/Registrar_Cliente.png")));
        btnCotizar.setHorizontalTextPosition(SwingConstants.CENTER);
        btnCotizar.setVerticalTextPosition(SwingConstants.BOTTOM);

        //registro_de_cliente
        btnCotizar.addActionListener(evt -> {
            FormularioCliente dlg = new FormularioCliente(this);
            dlg.setVisible(true);
        });

        JButton btnVenta = new JButton("Generar Venta");
        btnVenta.setBackground(new Color(255, 255, 255));
        btnVenta.setIcon(new ImageIcon(Almacen.class.getResource("/Generar_Venta.png")));
        btnVenta.setHorizontalTextPosition(SwingConstants.CENTER);
        btnVenta.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton btnFactura = new JButton("Generar Factura");
        btnFactura.setBackground(new Color(255, 255, 255));
        btnFactura.setIcon(new ImageIcon(Almacen.class.getResource("/Factura.png")));
        btnFactura.setHorizontalTextPosition(SwingConstants.CENTER);
        btnFactura.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Abre el formulario de facturación
        btnFactura.addActionListener(e -> {
            Generar_Facturas factura = new Generar_Facturas(this);
            factura.setVisible(true);
        });

        JButton btnClean = new JButton("Limpiar Pantalla");
        btnClean.setBackground(new Color(255, 255, 255));
        btnClean.setIcon(new ImageIcon(Almacen.class.getResource("/limpiar pantalla.png")));
        btnClean.setHorizontalTextPosition(SwingConstants.CENTER);
        btnClean.setVerticalTextPosition(SwingConstants.BOTTOM);
        btnClean.addActionListener(e -> modeloTabla.setRowCount(0));

        accionesPanel.add(btnCotizar);
        accionesPanel.add(btnVenta);
        accionesPanel.add(btnFactura);
        accionesPanel.add(btnClean);

        ribbon.add(accionesPanel);

        String[] columnas = {"SKU", "Descripción", "Cantidad", "Precio Unitario", "Descuento", "Total"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        getContentPane().add(scrollTabla, BorderLayout.CENTER);

     // -------------------- ESTADO BD --------------------
        JLabel estadoBD = new JLabel();
        estadoBD.setFont(new Font("Segoe UI", Font.BOLD, 12));
        estadoBD.setOpaque(true);
        estadoBD.setHorizontalAlignment(SwingConstants.CENTER);
        estadoBD.setPreferredSize(new Dimension(200, 30));
        actualizarEstadoBD(estadoBD); // <-- actualiza colores y texto
        getContentPane().add(estadoBD, BorderLayout.SOUTH);

        // Timer para actualizar automáticamente cada 10s
        new javax.swing.Timer(10000, e -> actualizarEstadoBD(estadoBD)).start();
        
        // Crea el contenedor vertical
        JPanel contenedorSuperior = new JPanel();
        contenedorSuperior.setLayout(new BoxLayout(contenedorSuperior, BoxLayout.Y_AXIS));

        // Panel con el reloj
        JPanel panelReloj = new JPanel(new BorderLayout());
        panelReloj.setBackground(Color.WHITE);
        panelReloj.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 10));

        RelojMexico reloj = new RelojMexico();
        reloj.setForeground(new Color(0, 0, 0));
        reloj.setFont(new Font("Segoe UI", Font.BOLD, 14));
        reloj.setHorizontalAlignment(SwingConstants.LEFT);
        panelReloj.add(reloj, BorderLayout.WEST);

        // Agrega reloj y luego el ribbon original (el que ya contenía tus botones y estilos)
        contenedorSuperior.add(panelReloj);
        contenedorSuperior.add(ribbon); // Asegúrate que este contenga todos tus botones

        getContentPane().add(contenedorSuperior, BorderLayout.NORTH);

    }

    private void actualizarEstadoBD(JLabel estadoBD) {
        if (DBConnection.estaConectado()) {
            estadoBD.setText("Conectado a BD");
            estadoBD.setBackground(new Color(0, 153, 51));  // Verde
            estadoBD.setForeground(Color.WHITE);
        } else {
            estadoBD.setText("Sin conexión a BD");
            estadoBD.setBackground(new Color(204, 0, 0));   // Rojo
            estadoBD.setForeground(Color.WHITE);
        }
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            Ventas ventana = new Ventas();
            ventana.setVisible(true);
        });
    }
}
