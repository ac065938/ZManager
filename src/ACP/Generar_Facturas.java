package ACP;

import javax.swing.*;
import java.awt.*;

public class Generar_Facturas extends JDialog {

    public Generar_Facturas(JFrame parent) {
        super(parent, "Facturas", true);
        // Dimensiones ajustadas para un mejor espaciado y apariencia general
        setSize(794, 660);
        setLocationRelativeTo(parent);
        // Asegúrate de que la imagen LOGOZM.png esté en la ruta correcta del classpath
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png")));

        // Panel principal con GridBagLayout para un control preciso del diseño
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        
        // Borde vacío para un poco de espacio alrededor de los componentes
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Restricciones de GridBagLayout base
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // Los componentes se expanden horizontalmente

     // Título
        JLabel titleLabel = new JLabel("Datos de la Factura");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.gridwidth = 4;
        gbcTitle.anchor = GridBagConstraints.WEST;
        gbcTitle.insets = new Insets(8, 8, 8, 8);
        contentPanel.add(titleLabel, gbcTitle);

        // Separador visual
        JSeparator separator = new JSeparator();
        GridBagConstraints gbcSeparator = new GridBagConstraints();
        gbcSeparator.gridx = 0;
        gbcSeparator.gridy = 1;
        gbcSeparator.gridwidth = 4;
        gbcSeparator.fill = GridBagConstraints.HORIZONTAL;
        gbcSeparator.insets = new Insets(0, 0, 15, 0);
        contentPanel.add(separator, gbcSeparator);


        // Fila actual para la adición de componentes
        int currentRow = 2; // Comenzamos en la fila 2 después del título y separador

        // Serie y Folio
        currentRow = addRow(contentPanel, currentRow, "Serie", "", "Folio", "");
        
        //---------------------- Fecha_y_Vendedor_con_calendario----------------------
        // gbc ya fue declarada antes, solo reasignamos propiedades si es necesario
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridy = currentRow;

        // Etiqueta_Fecha
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Fecha:"), gbc);

        // Selector de fecha con JXDatePicker
        org.jdesktop.swingx.JXDatePicker datePicker = new org.jdesktop.swingx.JXDatePicker();
        datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        contentPanel.add(datePicker, gbc);

        // Etiqueta Vendedor
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Vendedor:"), gbc);

        // Campo de texto para Vendedor
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        JTextField vendedorField = createStyledTextField("");
        contentPanel.add(vendedorField, gbc);

        currentRow++;
        
        

        // Cliente y R. F. C.
        currentRow = addRow(contentPanel, currentRow, "Cliente", "", "R. F. C.", "");

        //Importe
        GridBagConstraints gbcImporteLabel = new GridBagConstraints();
        gbcImporteLabel.gridx = 0;
        gbcImporteLabel.gridy = currentRow;
        gbcImporteLabel.anchor = GridBagConstraints.EAST;
        gbcImporteLabel.insets = new Insets(8, 8, 8, 8);
        contentPanel.add(new JLabel("Importe:"), gbcImporteLabel);

        JTextField importeField = createStyledTextField("");
        GridBagConstraints gbcImporteField = new GridBagConstraints();
        gbcImporteField.gridx = 1;
        gbcImporteField.gridy = currentRow;
        gbcImporteField.fill = GridBagConstraints.HORIZONTAL;
        gbcImporteField.weightx = 0.5;
        gbcImporteField.insets = new Insets(8, 8, 8, 8);
        contentPanel.add(importeField, gbcImporteField);

        // Tipo factura
        GridBagConstraints gbcTipoFacturaLabel = new GridBagConstraints();
        gbcTipoFacturaLabel.gridx = 2;
        gbcTipoFacturaLabel.gridy = currentRow;
        gbcTipoFacturaLabel.anchor = GridBagConstraints.EAST;
        gbcTipoFacturaLabel.insets = new Insets(8, 8, 8, 8);
        contentPanel.add(new JLabel("Tipo factura:"), gbcTipoFacturaLabel);

        JComboBox<String> tipoFacturaCombo = new JComboBox<>(new String[]{"Contado", "Crédito", "Anticipo", "Otra"});
        tipoFacturaCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tipoFacturaCombo.setBackground(Color.WHITE);
        tipoFacturaCombo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        GridBagConstraints gbcTipoFacturaCombo = new GridBagConstraints();
        gbcTipoFacturaCombo.gridx = 3;
        gbcTipoFacturaCombo.gridy = currentRow;
        gbcTipoFacturaCombo.fill = GridBagConstraints.HORIZONTAL;
        gbcTipoFacturaCombo.insets = new Insets(8, 8, 8, 8);
        contentPanel.add(tipoFacturaCombo, gbcTipoFacturaCombo);

        currentRow++;

        // Descto y Moneda
        currentRow = addRow(contentPanel, currentRow, "Descto", "", "Moneda", "");
        // Tipo de cambio y Sub-Total
        currentRow = addRow(contentPanel, currentRow, "Tipo de cambio", "", "Sub-Total", "");
        // I. V. A. y Total
        currentRow = addRow(contentPanel, currentRow, "I. V. A.", "", "Total", "");

        // Checkbox "Factura activa"
        JCheckBox activaCheckBox = new JCheckBox("Factura activa");
        activaCheckBox.setBackground(Color.WHITE);
        activaCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        GridBagConstraints gbcCheck = new GridBagConstraints();
        gbcCheck.gridx = 0;
        gbcCheck.gridy = currentRow;
        gbcCheck.gridwidth = 4;
        gbcCheck.anchor = GridBagConstraints.WEST;
        gbcCheck.insets = new Insets(15, 8, 15, 8);
        contentPanel.add(activaCheckBox, gbcCheck);
        currentRow++;
        
     // ---------------------- BOTÓN: GUARDAR ----------------------
        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.setBackground(new Color(40, 167, 69)); // Verde Bootstrap
        guardarBtn.setForeground(Color.WHITE);
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setPreferredSize(new Dimension(100, 35));
        guardarBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Datos de factura guardados correctamente.");
        });
        
        GridBagConstraints gbcGuardar = new GridBagConstraints();
        gbcGuardar.gridx = 0;
        gbcGuardar.gridy = currentRow;
        gbcGuardar.gridwidth = 4;
        gbcGuardar.anchor = GridBagConstraints.CENTER;
        gbcGuardar.insets = new Insets(15, 8, 0, 8);
        contentPanel.add(guardarBtn, gbcGuardar);
        currentRow++;

     // ---------------------- BOTÓN: CERRAR ----------------------
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(220, 53, 69)); // Rojo Bootstrap
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setPreferredSize(new Dimension(100, 35));
        cerrarBtn.addActionListener(e -> dispose());

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = currentRow;
        gbcBtn.gridwidth = 4;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        gbcBtn.insets = new Insets(10, 8, 8, 8);
        contentPanel.add(cerrarBtn, gbcBtn);

        // Envuelve_el_panel_de_contenido_en_un_JScrollPane_para_que_sea_desplazable
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null); // Elimina_el_borde_del_scroll_pane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Velocidad_de_desplazamiento
        setContentPane(scrollPane);
    }

    /**
     * Agrega una fila de dos etiquetas y dos campos de texto al panel.
     *
     * @param panel  El JPanel al que se añadirán los componentes.
     * @param y      La fila actual en GridBagLayout.
     * @param label1 Texto de la primera etiqueta.
     * @param val1   Valor inicial del primer campo de texto.
     * @param label2 Texto de la segunda etiqueta.
     * @param val2   Valor inicial del segundo campo de texto.
     * @return La siguiente fila disponible.
     */
    private int addRow(JPanel panel, int y, String label1, String val1, String label2, String val2) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Espaciado consistente
        gbc.gridy = y;

        // Primer par de etiqueta y campo de texto
        if (!label1.isEmpty()) {
            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha
            panel.add(new JLabel(label1 + ":"), gbc);

            gbc.gridx = 1;
            gbc.fill = GridBagConstraints.HORIZONTAL; // El campo de texto se expande
            gbc.weightx = 0.5; // Asigna peso para distribución horizontal
            JTextField field1 = createStyledTextField(val1);
            panel.add(field1, gbc);
        }

        // Segundo par de etiqueta y campo de texto
        if (!label2.isEmpty()) {
            gbc.gridx = 2;
            gbc.fill = GridBagConstraints.NONE; // No expandir horizontalmente
            gbc.anchor = GridBagConstraints.EAST; // Alinea la etiqueta a la derecha
            panel.add(new JLabel(label2 + ":"), gbc);

            gbc.gridx = 3;
            gbc.fill = GridBagConstraints.HORIZONTAL; // El campo de texto se expande
            gbc.weightx = 0.5; // Asigna peso
            JTextField field2 = createStyledTextField(val2);
            panel.add(field2, gbc);
        }

        return y + 1; // Retorna la siguiente fila disponible
    }

    /**
     * Crea un JTextField con un estilo uniforme.
     * @param text El texto inicial del campo.
     * @return Un JTextField estilizado.
     */
    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Fuente y tamaño consistente
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1), // Borde más suave
            BorderFactory.createEmptyBorder(5, 8, 5, 8) // Padding interno
        ));
        return field;
    }
}