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
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30)); // No debería interferir

        
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
        int currentRow = 2; 

        // Serie y Folio
        currentRow = addRow(contentPanel, currentRow, "Serie", "", "Folio", "");
        
     // Etiqueta Fecha
     GridBagConstraints gbcFechaLabel = new GridBagConstraints();
     gbcFechaLabel.gridx = 0;
     gbcFechaLabel.gridy = currentRow;
     gbcFechaLabel.anchor = GridBagConstraints.EAST;
     gbcFechaLabel.insets = new Insets(8, 8, 8, 8);
     contentPanel.add(new JLabel("Fecha:"), gbcFechaLabel);

     // Campo Fecha (DatePicker)
     org.jdesktop.swingx.JXDatePicker datePicker = new org.jdesktop.swingx.JXDatePicker();
     datePicker.setFont(new Font("Segoe UI", Font.PLAIN, 14));
     GridBagConstraints gbcFechaPicker = new GridBagConstraints();
     gbcFechaPicker.gridx = 1;
     gbcFechaPicker.gridy = currentRow;
     gbcFechaPicker.fill = GridBagConstraints.HORIZONTAL;
     gbcFechaPicker.weightx = 0.5;
     gbcFechaPicker.insets = new Insets(8, 8, 8, 8);
     contentPanel.add(datePicker, gbcFechaPicker);

     // Etiqueta Vendedor
     GridBagConstraints gbcVendedorLabel = new GridBagConstraints();
     gbcVendedorLabel.gridx = 2;
     gbcVendedorLabel.gridy = currentRow;
     gbcVendedorLabel.anchor = GridBagConstraints.EAST;
     gbcVendedorLabel.insets = new Insets(8, 8, 8, 8);
     contentPanel.add(new JLabel("Vendedor:"), gbcVendedorLabel);

     // Campo Vendedor (JTextField)
     JTextField vendedorField = createStyledTextField("");
     GridBagConstraints gbcVendedorField = new GridBagConstraints();
     gbcVendedorField.gridx = 3;
     gbcVendedorField.gridy = currentRow;
     gbcVendedorField.fill = GridBagConstraints.HORIZONTAL;
     gbcVendedorField.weightx = 0.5;
     gbcVendedorField.insets = new Insets(8, 8, 8, 8);
     contentPanel.add(vendedorField, gbcVendedorField);

     currentRow++;

     // Para "Cliente" y "R.F.C.", usa addRow porque no agregas manualmente esos campos:
     currentRow = addRow(contentPanel, currentRow, "Cliente", "", "R. F. C.", "");

     // Para la fila "Importe" y "Tipo factura", hazlo manualmente:

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

     // Luego continúa con addRow para las filas restantes, por ejemplo:
     currentRow = addRow(contentPanel, currentRow, "Descto", "", "Moneda", "");
     currentRow = addRow(contentPanel, currentRow, "Tipo de cambio", "", "Sub-Total", "");
     currentRow = addRow(contentPanel, currentRow, "I. V. A.", "", "Total", "");

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
        
        //Crear un JPanel para los botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Espaciado horizontal y vertical
        buttonPanel.setBackground(Color.WHITE); // Fondo blanco

        //---------------------- BOTÓN: GUARDAR ----------------------
        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.setBackground(new Color(40, 167, 69)); // Verde Bootstrap
        guardarBtn.setForeground(Color.BLACK);
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setPreferredSize(new Dimension(100, 35));
        guardarBtn.setFocusPainted(false); // Eliminar el contorno de enfoque
        guardarBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borde visible
        guardarBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Datos de factura guardados correctamente.");
        });
        buttonPanel.add(guardarBtn);

        //---------------------- BOTÓN: CERRAR ----------------------
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setBackground(new Color(220, 53, 69)); // Rojo Bootstrap
        cerrarBtn.setForeground(Color.BLACK); // Cambiar a blanco para mejor contraste
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setPreferredSize(new Dimension(100, 35));
        cerrarBtn.setFocusPainted(false); // Eliminar el contorno de enfoque
        cerrarBtn.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // Borde visible
        cerrarBtn.addActionListener(e -> dispose());
        buttonPanel.add(cerrarBtn);

        // Agregar el panel de botones al panel de contenido
        GridBagConstraints gbcButtonPanel = new GridBagConstraints();
        gbcButtonPanel.gridx = 0;
        gbcButtonPanel.gridy = currentRow;
        gbcButtonPanel.gridwidth = 4;
        gbcButtonPanel.anchor = GridBagConstraints.CENTER;
        gbcButtonPanel.insets = new Insets(15, 8, 0, 8);
        contentPanel.add(buttonPanel, gbcButtonPanel);

        // Envuelve_el_panel_de_contenido_en_un_JScrollPane_para_que_sea_desplazable
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null); // Elimina_el_borde_del_scroll_pane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Velocidad_de_desplazamiento
        setContentPane(scrollPane);
    }

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