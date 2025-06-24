package ACP;

import ACP.Ventas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;

// Importar_Apache POI
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class Almacen extends JFrame {

    private JLabel statusLabel;
    private JTable table; // Tabla_a_nivel_de_clase

    public Almacen() {
        setTitle("ZManager 2.0 - Almacén");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        // Carga la imagen desde el classpath
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));
        // Fija el icono de la ventana
        setIconImage(icon);

        // ---------- Barra_tipo_Ribbon ----------
        JToolBar ribbon = new JToolBar();
        ribbon.setBackground(new Color(255, 255, 255));
        ribbon.setFloatable(false);
        ribbon.setLayout(new FlowLayout(FlowLayout.LEFT));

        // ---------- Buscar_Producto ----------
        JPanel productoBusquedaPanel = new JPanel(new BorderLayout());
        productoBusquedaPanel.setBackground(new Color(255, 255, 255));
        productoBusquedaPanel.setPreferredSize(new Dimension(210, 107));
        productoBusquedaPanel.setBorder(BorderFactory.createTitledBorder("Buscar Producto"));

        JPanel filaSKUProducto = new JPanel(new GridLayout(1, 2, 5, 0));

        JTextField campoSkuProducto = new JTextField("%SKU%");
        campoSkuProducto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoSkuProducto.getText().equals("%SKU%")) campoSkuProducto.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoSkuProducto.getText().trim().isEmpty()) campoSkuProducto.setText("%SKU%");
            }
        });

        JTextField campoFiltroProducto = new JTextField("%Filtro%");
        campoFiltroProducto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoFiltroProducto.getText().equals("%Filtro%")) campoFiltroProducto.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoFiltroProducto.getText().trim().isEmpty()) campoFiltroProducto.setText("%Filtro%");
            }
        });

        filaSKUProducto.add(campoSkuProducto);
        filaSKUProducto.add(campoFiltroProducto);

        //Campo_de_características_corregido
        JTextField campoCaracteristicas = new JTextField("Caracteristicas");
        campoCaracteristicas.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoCaracteristicas.getText().equals("Caracteristicas")) campoCaracteristicas.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoCaracteristicas.getText().trim().isEmpty()) campoCaracteristicas.setText("Caracteristicas");
            }
        });

        productoBusquedaPanel.add(filaSKUProducto, BorderLayout.NORTH);
        productoBusquedaPanel.add(campoCaracteristicas, BorderLayout.CENTER);

        JCheckBox limpiarAntesCheckbox = new JCheckBox("Limpiar antes de cargar información");
        limpiarAntesCheckbox.setBackground(new Color(255, 255, 255));
        limpiarAntesCheckbox.setSelected(true);
        productoBusquedaPanel.add(limpiarAntesCheckbox, BorderLayout.SOUTH);

        ribbon.add(productoBusquedaPanel);
        
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

        //Sección_Acciones
        JPanel accionesPanel = new JPanel(new FlowLayout());
        accionesPanel.setBackground(new Color(255, 255, 255));
        accionesPanel.setBorder(BorderFactory.createTitledBorder("Acciones"));

        JButton readFileButton = new JButton("Leer archivo");
        readFileButton.setBackground(new Color(255, 255, 255));
        readFileButton.setIcon(new ImageIcon(Almacen.class.getResource("/icono_leer_archivo.png")));
        readFileButton.setHorizontalTextPosition(SwingConstants.CENTER);
        readFileButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton printLabelButton = new JButton("Imprimir etiquetas");
        printLabelButton.setBackground(new Color(255, 255, 255));
        printLabelButton.setIcon(new ImageIcon(Almacen.class.getResource("/icono_imprimir_etiqueta.png")));
        printLabelButton.setHorizontalTextPosition(SwingConstants.CENTER);
        printLabelButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton clearRecordsButton = new JButton("Limpiar Pantalla");
        clearRecordsButton.setBackground(new Color(255, 255, 255));
        clearRecordsButton.setIcon(new ImageIcon(Almacen.class.getResource("/limpiar pantalla.png")));
        clearRecordsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        clearRecordsButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton exportButton = new JButton("Exportar a Excel");
        exportButton.setBackground(new Color(255, 255, 255));
        exportButton.setIcon(new ImageIcon(Almacen.class.getResource("/icono_excel.png")));
        exportButton.setHorizontalTextPosition(SwingConstants.CENTER);
        exportButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        exportButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como");
            fileChooser.setSelectedFile(new java.io.File("almacen.csv"));

            int userSelection = fileChooser.showSaveDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                java.io.File fileToSave = fileChooser.getSelectedFile();
                exportarTablaAExcel(table, fileToSave.getAbsolutePath());
            }
            
        });
        
        

        accionesPanel.add(readFileButton);
        accionesPanel.add(printLabelButton);
        accionesPanel.add(clearRecordsButton);
        accionesPanel.add(exportButton);

        ribbon.add(accionesPanel);
        getContentPane().add(ribbon, BorderLayout.NORTH);

        //Tabla
        String[] columnNames = {
            "Código", "Descripción", "Línea", "Precio", "Ventas", "Almacén", "Escaner", "Descto", "Descont"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        //Estado
        statusLabel = new JLabel("Estado: verificando conexión...");
        statusLabel.setBackground(new Color(78, 186, 222));
        statusLabel.setForeground(Color.DARK_GRAY);
        getContentPane().add(statusLabel, BorderLayout.SOUTH);

        actualizarEstadoConexion();
        new javax.swing.Timer(10000, e -> actualizarEstadoConexion()).start();

        //Menú principal
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        // Estilo común
        Dimension buttonSize = new Dimension(80, 60);

        // -------------------- BOTÓN: VENTAS --------------------
        JButton ventasBtn = new JButton("Ventas", new ImageIcon(getClass().getResource("/ventas.png")));
        ventasBtn.setFont(new Font("Tahoma", Font.BOLD, 11));
        ventasBtn.setPreferredSize(new Dimension(80, 73));
        ventasBtn.setToolTipText("Ir a Ventas");
        ventasBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        ventasBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        ventasBtn.setBackground(Color.WHITE);
        ventasBtn.addActionListener(e -> Navegador.irA(this, Ventas.class));
        menuBar.add(ventasBtn);

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

        // -------------------- BOTÓN: CERRAR SESIÓN --------------------
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
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });
        menuBar.add(cerrarSesionBtn);


    }

    private void exportarTablaAExcel(JTable table, String path) {
        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Inventario");

            // Crear_encabezados_incluso_si_no_hay_datos
            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(table.getColumnName(i));
            }

            // Si_hay_datos_agregarlos
            if (table.getRowCount() > 0) {
                for (int row = 0; row < table.getRowCount(); row++) {
                    org.apache.poi.ss.usermodel.Row excelRow = sheet.createRow(row + 1);
                    for (int col = 0; col < table.getColumnCount(); col++) {
                        Object value = table.getValueAt(row, col);
                        org.apache.poi.ss.usermodel.Cell cell = excelRow.createCell(col);
                        cell.setCellValue(value != null ? value.toString() : "");
                    }
                }
            }

            try (java.io.FileOutputStream fos = new java.io.FileOutputStream(path)) {
                workbook.write(fos);
            }

            JOptionPane.showMessageDialog(this, "Archivo exportado con éxito:\n" + path);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al exportar: " + ex.getMessage());
        }
    }

    private void actualizarEstadoConexion() {
        boolean conectado = DBConnection.estaConectado();
        if (conectado) {
            statusLabel.setText("Estado: conectado a la base de datos");
            statusLabel.setForeground(new Color(0, 128, 0));
        } else {
            statusLabel.setText("Estado: sin conexión a la base de datos");
            statusLabel.setForeground(Color.RED);
        }
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            Almacen ventana = new Almacen();
            ventana.setVisible(true);
        });
    }
}