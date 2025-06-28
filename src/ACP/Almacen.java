package ACP;

import ACP.Ventas;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.datatransfer.StringSelection;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.FileOutputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;

// Importar_Apache POI
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class Almacen extends JFrame {

    private JLabel statusLabel;
    private JTable table; 
    private boolean mostrarConDescuento = false;
    private JTextField campoSkuProducto;
    private JTextField campoFiltroProducto;
    private JTextField campoCaracteristicas;
    private boolean ocultarDescontinuados = false;
    private String ultimoSkuFiltro = "";
    private String ultimoFiltroTexto = "";
    private String ultimoCaracteristicas = "";

    public Almacen() {
        setTitle("ZManager 2.0 - Almacén");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());
        
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));

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

        // Primero declarar los campos
        campoSkuProducto = new JTextField("%SKU%");
        campoFiltroProducto = new JTextField("%Filtro%");
        campoCaracteristicas = new JTextField("Caracteristicas");

        campoSkuProducto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoSkuProducto.getText().equals("%SKU%")) campoSkuProducto.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoSkuProducto.getText().trim().isEmpty()) campoSkuProducto.setText("%SKU%");
            }
        });

        campoFiltroProducto.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoFiltroProducto.getText().equals("%Filtro%")) campoFiltroProducto.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoFiltroProducto.getText().trim().isEmpty()) campoFiltroProducto.setText("%Filtro%");
            }
        });

        campoCaracteristicas.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (campoCaracteristicas.getText().equals("Caracteristicas")) campoCaracteristicas.setText("");
            }

            public void focusLost(FocusEvent e) {
                if (campoCaracteristicas.getText().trim().isEmpty()) campoCaracteristicas.setText("Caracteristicas");
            }
        });

        campoSkuProducto.addActionListener(e -> {
            buscarProducto(campoSkuProducto.getText(), campoFiltroProducto.getText(), campoCaracteristicas.getText());
            campoSkuProducto.setText("%SKU%");
            campoFiltroProducto.setText("%Filtro%");
            campoCaracteristicas.setText("Caracteristicas");
        });

        campoFiltroProducto.addActionListener(e -> {
            buscarProducto(campoSkuProducto.getText(), campoFiltroProducto.getText(), campoCaracteristicas.getText());
            campoSkuProducto.setText("%SKU%");
            campoFiltroProducto.setText("%Filtro%");
            campoCaracteristicas.setText("Caracteristicas");
        });

        campoCaracteristicas.addActionListener(e -> {
            buscarProducto(campoSkuProducto.getText(), campoFiltroProducto.getText(), campoCaracteristicas.getText());
            campoSkuProducto.setText("%SKU%");
            campoFiltroProducto.setText("%Filtro%");
            campoCaracteristicas.setText("Caracteristicas");
        });
       
        filaSKUProducto.add(campoSkuProducto);
        filaSKUProducto.add(campoFiltroProducto);

        productoBusquedaPanel.add(filaSKUProducto, BorderLayout.NORTH);
        productoBusquedaPanel.add(campoCaracteristicas, BorderLayout.CENTER);
        
        ribbon.add(productoBusquedaPanel);
        
        JPanel filtrosPanel = new JPanel(new GridLayout(2, 1));
        filtrosPanel.setBackground(new Color(255, 255, 255));
        filtrosPanel.setPreferredSize(new Dimension(175, 105));
        filtrosPanel.setBorder(BorderFactory.createTitledBorder("Filtros"));

        JButton btnConDescuento = new JButton("Solo descuentos");
        btnConDescuento.setBackground(new Color(255, 255, 255));
        btnConDescuento.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        filtrosPanel.add(btnConDescuento);

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
        
        clearRecordsButton.addActionListener(e -> {
            DefaultTableModel modelo = (DefaultTableModel) table.getModel();
            modelo.setRowCount(0);
        });

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
        
        JButton btnOcultarDescontinuados = new JButton("Ocultar descontinuados");
        btnOcultarDescontinuados.setBackground(new Color(255, 255, 255));
        btnOcultarDescontinuados.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        filtrosPanel.add(btnOcultarDescontinuados);

        btnOcultarDescontinuados.addActionListener(e -> {
            DefaultTableModel modelo = (DefaultTableModel) table.getModel();
            
            for (int i = modelo.getRowCount() - 1; i >= 0; i--) {
            	String estado = modelo.getValueAt(i, 9).toString();
                if (estado.equalsIgnoreCase("Sí")) {
                    modelo.removeRow(i);
                }
            }
        });
        
        btnConDescuento.addActionListener(e -> {
            mostrarConDescuento = !mostrarConDescuento; 

            btnConDescuento.setText(mostrarConDescuento ? "Todos los productos" : "Solo descuentos");

            // Si no hay filtros previos, usar los actuales (por primera vez)
            if (ultimoSkuFiltro.isEmpty()) ultimoSkuFiltro = campoSkuProducto.getText().trim();
            if (ultimoFiltroTexto.isEmpty()) ultimoFiltroTexto = campoFiltroProducto.getText().trim();
            if (ultimoCaracteristicas.isEmpty()) ultimoCaracteristicas = campoCaracteristicas.getText().trim();

            // Lógica para limpiar si los filtros tienen texto por defecto
            if (ultimoSkuFiltro.equalsIgnoreCase("%SKU%")) ultimoSkuFiltro = "";
            if (ultimoFiltroTexto.equalsIgnoreCase("%Filtro%")) ultimoFiltroTexto = "";
            if (ultimoCaracteristicas.equalsIgnoreCase("Caracteristicas")) ultimoCaracteristicas = "";

            cargarProductosDesdeBD(
                ultimoSkuFiltro,
                ultimoFiltroTexto,
                ultimoCaracteristicas,
                mostrarConDescuento,
                ocultarDescontinuados
            );
        });

        accionesPanel.add(readFileButton);
        accionesPanel.add(printLabelButton);
        accionesPanel.add(clearRecordsButton);
        accionesPanel.add(exportButton);

        ribbon.add(accionesPanel);
        getContentPane().add(ribbon, BorderLayout.NORTH);

        //Tabla
        String[] columnNames = {
            "Código", "Descripción", "Línea", "Precio", "Ventas", "Almacen", "Escaner", "Medida", "Descto", "Descont"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(tableModel);
        
     // Permitir copiar con Ctrl+C
        table.getInputMap().put(KeyStroke.getKeyStroke("control C"), "copy");
        table.getActionMap().put("copy", new AbstractAction() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if (row >= 0 && col >= 0) {
                    Object value = table.getValueAt(row, col);
                    StringSelection selection = new StringSelection(value.toString());
                    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, null);
                }
            }
        });

        
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // -------------------- ESTADO BD --------------------
        JLabel estadoBD = new JLabel();
        estadoBD.setFont(new Font("Segoe UI", Font.BOLD, 12));
        estadoBD.setOpaque(true);
        estadoBD.setHorizontalAlignment(SwingConstants.CENTER);
        estadoBD.setPreferredSize(new Dimension(200, 30));
        actualizarEstadoBD(estadoBD); // <-- actualiza_colores_y_texto
        getContentPane().add(estadoBD, BorderLayout.SOUTH);

        // Timer_para_actualizar_automáticamente_cada_10s
        new javax.swing.Timer(10000, e -> actualizarEstadoBD(estadoBD)).start();

        //Menú_principal
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(Color.WHITE);
        setJMenuBar(menuBar);

        // Estilo_común
        Dimension buttonSize = new Dimension(80, 60);

        //-------------------- BOTÓN: VENTAS --------------------
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
                MainApp.mostrarLogin(); // Vuelve a ejecutar el login con listener
            }
            
        });
        menuBar.add(cerrarSesionBtn);

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

        // Agrega reloj y luego el ribbon original (el que ya contenía botones y estilos)
        contenedorSuperior.add(panelReloj);
        contenedorSuperior.add(ribbon); 

        getContentPane().add(contenedorSuperior, BorderLayout.NORTH);
        
    }

    private void exportarTablaAExcel(JTable table, String path) {
        try (org.apache.poi.xssf.usermodel.XSSFWorkbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.createSheet("Inventario");

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int col = 0; col < table.getColumnCount(); col++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(col);
                cell.setCellValue(table.getColumnName(col));
            }

            for (int row = 0; row < table.getRowCount(); row++) {
                org.apache.poi.ss.usermodel.Row excelRow = sheet.createRow(row + 1);
                for (int col = 0; col < table.getColumnCount(); col++) {
                    Object value = table.getValueAt(row, col);
                    org.apache.poi.ss.usermodel.Cell cell = excelRow.createCell(col);
                    cell.setCellValue(value != null ? value.toString() : "");
                }
            }

            for (int col = 0; col < table.getColumnCount(); col++) {
                sheet.autoSizeColumn(col);
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

    private void actualizarEstadoBD(JLabel estadoBD) {
        if (DBConnection.estaConectado()) {
            estadoBD.setText("Conectado a BD");
            estadoBD.setBackground(new Color(0, 153, 51));
            estadoBD.setForeground(Color.WHITE);
        } else {
            estadoBD.setText("Sin conexión a BD");
            estadoBD.setBackground(new Color(204, 0, 0));
            estadoBD.setForeground(Color.WHITE);
        }
    }
    
    private void cargarProductosDesdeBD(String codigoFiltro, String textoFiltro, String descripcionFiltro, boolean soloConDescuento, boolean ocultarDescontinuados) {
        DefaultTableModel modelo = (DefaultTableModel) table.getModel();
        modelo.setRowCount(0); 
        StringBuilder sql = new StringBuilder(
            "SELECT codigo, descripcion, linea, precio, ventas, almacen, escaner, medida, descto, descont FROM almacen WHERE 1=1"
        );

        List<String> filtros = new ArrayList<>();

        if (!codigoFiltro.trim().isEmpty() && !codigoFiltro.equalsIgnoreCase("%SKU%")) {
            sql.append(" AND codigo LIKE ?");
            filtros.add("%" + codigoFiltro.trim() + "%");
        }

        if (!textoFiltro.trim().isEmpty() && !textoFiltro.equalsIgnoreCase("%Filtro%")) {
            sql.append(" AND descripcion LIKE ?");
            filtros.add("%" + textoFiltro.trim() + "%");
        }

        if (!descripcionFiltro.trim().isEmpty() && !descripcionFiltro.equalsIgnoreCase("Caracteristicas")) {
            sql.append(" AND (linea LIKE ? OR descripcion LIKE ?)");
            filtros.add("%" + descripcionFiltro.trim() + "%");
            filtros.add("%" + descripcionFiltro.trim() + "%");
        }
        
        if (soloConDescuento) {
            sql.append(" AND descto > 0");
        }
        
        if (ocultarDescontinuados) {
            sql.append(" AND descont = 0");
        }

        try (PreparedStatement stmt = DBConnection.conectar().prepareStatement(sql.toString())) {
            for (int i = 0; i < filtros.size(); i++) {
                stmt.setString(i + 1, filtros.get(i));
            }

            ResultSet rs = stmt.executeQuery();
            int contador = 0;

            while (rs.next()) {
                Object[] fila = {
                    rs.getString("codigo"),
                    rs.getString("descripcion"),
                    rs.getString("linea"),
                    rs.getDouble("precio"),
                    rs.getInt("ventas"),
                    rs.getString("almacen"),
                    rs.getString("escaner"),
                    rs.getString("medida"),
                    rs.getDouble("descto"),
                    rs.getBoolean("descont") ? "Sí" : "No"
                };
                modelo.addRow(fila);
                contador++;
            }

            if (contador == 0) {
                JOptionPane.showMessageDialog(this, "No se encontraron productos con los filtros ingresados.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al consultar la base de datos: " + ex.getMessage());
        }
    }

    private void buscarProducto(String sku, String filtro, String caracteristicas) {
        // Guardar los filtros usados
        ultimoSkuFiltro = sku;
        ultimoFiltroTexto = filtro;
        ultimoCaracteristicas = caracteristicas;
        cargarProductosDesdeBD(
                sku,
                filtro,
                caracteristicas,
                mostrarConDescuento,
                ocultarDescontinuados
        );
    }

    public static void mostrar() {
        SwingUtilities.invokeLater(() -> {
            Almacen ventana = new Almacen();
            ventana.setVisible(true);
        });
    }
}