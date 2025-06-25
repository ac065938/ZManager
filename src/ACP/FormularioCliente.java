package ACP;

import javax.swing.*;
import java.awt.*;

public class FormularioCliente extends JDialog {

    public FormularioCliente(JFrame parent) {
        super(parent, "Registrar Cliente", true);
        setSize(700, 520);
        setLocationRelativeTo(parent);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png")));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titleLabel = new JLabel("Datos del Cliente");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        contentPanel.add(titleLabel, gbc);

        // Separador
        JSeparator separator = new JSeparator();
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        contentPanel.add(separator, gbc);
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridwidth = 1;

        int currentRow = 2;

        // Pares de campos
        currentRow = addRow(contentPanel, currentRow, "Número", "0001", "Nombre", "Juan Pérez");
        currentRow = addRow(contentPanel, currentRow, "Razón Social", "Empresa S.A. de C.V.", "RFC", "XAXX010101000");
        currentRow = addRow(contentPanel, currentRow, "Tel", "00 000 000 0000", "Fax", "00 000 0000");
        currentRow = addRow(contentPanel, currentRow, "Cel", "000 000 0000", "Nextel", "Nextel ID");
        currentRow = addRow(contentPanel, currentRow, "Email", "correo@ejemplo.com", "Calle", "Av. Principal");
        currentRow = addRow(contentPanel, currentRow, "Interior", "1", "Exterior", "2");
        currentRow = addRow(contentPanel, currentRow, "Cruce", "Con Calle 2", "Referencia", "Cerca del parque");
        currentRow = addRow(contentPanel, currentRow, "Colonia", "Centro", "Código Postal", "12345");
        currentRow = addRow(contentPanel, currentRow, "Ciudad", "Zaragoza", "Municipio", "Zaragoza");

        // Estado (ComboBox)
        gbc.gridx = 0;
        gbc.gridy = currentRow;
        gbc.anchor = GridBagConstraints.EAST;
        contentPanel.add(new JLabel("Estado:"), gbc);

        JComboBox<String> estadoCombo = new JComboBox<>(new String[]{
            "-", "Aguascalientes", "Baja California", "Campeche", "CDMX", "Chiapas", "Chihuahua", "Durango",
            "Estado de México", "Guanajuato", "Guerrero", "Jalisco", "Michoacán", "Morelos", "Nuevo León",
            "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco",
            "Tamaulipas", "Veracruz", "Yucatán", "Zacatecas"
        });
        estadoCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        estadoCombo.setBackground(Color.WHITE);
        estadoCombo.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        contentPanel.add(estadoCombo, gbc);
        currentRow++;

     // Checkbox Activo
        JCheckBox statusCheck = new JCheckBox("Activo");
        statusCheck.setSelected(true);
        statusCheck.setBackground(Color.WHITE);
        statusCheck.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        GridBagConstraints cCheck = new GridBagConstraints();
        cCheck.insets = new Insets(8, 10, 8, 10);
        cCheck.gridx = 0;
        cCheck.gridy = currentRow;
        cCheck.gridwidth = 2;
        cCheck.anchor = GridBagConstraints.WEST;
        contentPanel.add(statusCheck, cCheck);
        currentRow++;

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBotones.setBackground(Color.WHITE);

        JButton guardarBtn = new JButton("Guardar");
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setBackground(new Color(40, 167, 69));
        guardarBtn.setForeground(Color.WHITE);           
        guardarBtn.setFocusPainted(false);
        guardarBtn.setBorderPainted(false);
        guardarBtn.setOpaque(true);
        guardarBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Cliente guardado o actualizado correctamente.");
        });

        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cerrarBtn.setBackground(new Color(108, 117, 125));
        cerrarBtn.setForeground(Color.WHITE);
        cerrarBtn.setFocusPainted(false);
        cerrarBtn.setBorderPainted(false);
        cerrarBtn.setOpaque(true);
        cerrarBtn.addActionListener(e -> dispose());

        panelBotones.add(guardarBtn);
        panelBotones.add(cerrarBtn);

        GridBagConstraints cBtns = new GridBagConstraints();
        cBtns.insets = new Insets(20, 10, 20, 10);
        cBtns.gridx = 0;
        cBtns.gridy = currentRow;
        cBtns.gridwidth = 4;
        cBtns.anchor = GridBagConstraints.CENTER;
        contentPanel.add(panelBotones, cBtns);

        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        setContentPane(scroll);

    }

    private int addRow(JPanel panel, int y, String label1, String val1, String label2, String val2) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.gridy = y;

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(label1 + ":"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        panel.add(createStyledTextField(val1), gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(label2 + ":"), gbc);
        gbc.gridx = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 0.5;
        panel.add(createStyledTextField(val2), gbc);

        return y + 1;
    }

    private JTextField createStyledTextField(String placeholder) {
        JTextField field = new JTextField(placeholder);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setForeground(Color.GRAY); // Texto inicial en gris

        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        // Guardar el placeholder como variable final
        final String defaultText = placeholder;

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (field.getText().equals(defaultText)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(defaultText);
                    field.setForeground(Color.GRAY);
                }
            }
        });

        return field;
    }

}
