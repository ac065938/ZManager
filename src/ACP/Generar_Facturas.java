package ACP;

import javax.swing.*;
import java.awt.*;

public class Generar_Facturas extends JDialog {

    public Generar_Facturas(JFrame parent) {
        super(parent, "Facturas", true);
        setSize(600, 500);
        setLocationRelativeTo(parent);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png")));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        JLabel titleLabel = new JLabel("Datos de la Factura");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 4;
        contentPanel.add(titleLabel, gbc);
        gbc.gridwidth = 1;

        int y = 1;
        y = addField(contentPanel, y, "Serie", "");
        y = addField(contentPanel, y, "Folio", "");
        y = addField(contentPanel, y, "Fecha", "");
        y = addField(contentPanel, y, "Vendedor", "");
        y = addField(contentPanel, y, "Cliente", "");
        y = addField(contentPanel, y, "R. F. C.", "");
        y = addField(contentPanel, y, "Importe", "");

        // Tipo Factura (ComboBox)
        gbc.gridx = 0;
        gbc.gridy = y;
        contentPanel.add(new JLabel("Tipo factura:"), gbc);

        JComboBox<String> tipoFacturaCombo = new JComboBox<>(new String[] {
            "Contado", "Crédito", "Anticipo", "Otra"
        });
        gbc.gridx = 1;
        gbc.gridy = y++;
        contentPanel.add(tipoFacturaCombo, gbc);

        y = addField(contentPanel, y, "Descto", "");
        y = addField(contentPanel, y, "Moneda", "");
        y = addField(contentPanel, y, "Tipo de cambio", "");
        y = addField(contentPanel, y, "Sub-Total", "");
        y = addField(contentPanel, y, "I. V. A.", "");
        y = addField(contentPanel, y, "Total", "");

        // Factura activa
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        JCheckBox activaCheck = new JCheckBox("Factura activa");
        contentPanel.add(activaCheck, gbc);
        gbc.gridwidth = 1;

        // Botón cerrar
        gbc.gridx = 0;
        gbc.gridy = y++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dispose());
        contentPanel.add(cerrarBtn, gbc);

        setContentPane(contentPanel);
    }

    private int addField(JPanel panel, int y, String label, String placeholder) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label + ":"), gbc);

        gbc.gridx = 1;
        gbc.gridy = y;
        JTextField field = new JTextField(placeholder);
        panel.add(field, gbc);

        return y + 1;
    }
}
