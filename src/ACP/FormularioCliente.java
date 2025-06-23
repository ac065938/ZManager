package ACP;

import javax.swing.*;
import java.awt.*;

public class FormularioCliente extends JDialog {

    public FormularioCliente(JFrame parent) {
        super(parent, "Registrar Cliente", true);
        setSize(687, 416);
        setLocationRelativeTo(parent);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png")));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(Color.WHITE);

        int y = 0;
        y = agregarCampo(contentPanel, y, "Número", "0001");
        y = agregarCampo(contentPanel, y, "Nombre", "Juan Pérez");
        y = agregarCampo(contentPanel, y, "Razón Social", "Empresa S.A. de C.V.");
        y = agregarCampo(contentPanel, y, "RFC", "XAXX010101000");
        y = agregarCampo(contentPanel, y, "Tel", "00 000 000 0000");
        y = agregarCampo(contentPanel, y, "Fax", "00 000 0000");
        y = agregarCampo(contentPanel, y, "Cel", "000 000 0000");
        y = agregarCampo(contentPanel, y, "Nextel", "Nextel ID");
        y = agregarCampo(contentPanel, y, "Email", "correo@ejemplo.com");
        y = agregarCampo(contentPanel, y, "Calle", "Av. Principal");
        y = agregarCampo(contentPanel, y, "Interior", "1");
        y = agregarCampo(contentPanel, y, "Exterior", "2");
        y = agregarCampo(contentPanel, y, "Cruce", "Con Calle 2");
        y = agregarCampo(contentPanel, y, "Referencia", "Cerca del parque");
        y = agregarCampo(contentPanel, y, "Colonia", "Centro");
        y = agregarCampo(contentPanel, y, "Código Postal", "12345");
        y = agregarCampo(contentPanel, y, "Ciudad", "Zaragoza");
        y = agregarCampo(contentPanel, y, "Municipio", "Zaragoza");

        // Campo Estado (ComboBox)
        GridBagConstraints cLabel = new GridBagConstraints();
        cLabel.insets = new Insets(8, 10, 8, 10);
        cLabel.fill = GridBagConstraints.HORIZONTAL;
        cLabel.gridx = 0;
        cLabel.gridy = y;
        contentPanel.add(new JLabel("Estado:"), cLabel);

        String[] estados = {
            "-", "Aguascalientes", "Baja California", "Campeche", "CDMX", "Chiapas", "Chihuahua", "Durango",
            "Estado de México", "Guanajuato", "Guerrero", "Jalisco", "Michoacán", "Morelos", "Nuevo León",
            "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco",
            "Tamaulipas", "Veracruz", "Yucatán", "Zacatecas"
        };

        JComboBox<String> estadoCombo = new JComboBox<>(estados);

        GridBagConstraints cCombo = new GridBagConstraints();
        cCombo.insets = new Insets(8, 10, 8, 10);
        cCombo.fill = GridBagConstraints.HORIZONTAL;
        cCombo.gridx = 1;
        cCombo.gridy = y;
        cCombo.weightx = 1.0;
        contentPanel.add(estadoCombo, cCombo);
        y++;

        // Checkbox Activo
        GridBagConstraints cCheck = new GridBagConstraints();
        cCheck.insets = new Insets(8, 10, 8, 10);
        cCheck.gridx = 0;
        cCheck.gridy = y;
        cCheck.gridwidth = 2;
        JCheckBox statusCheck = new JCheckBox("Activo");
        statusCheck.setSelected(true);
        contentPanel.add(statusCheck, cCheck);
        y++;

        // Botón Cerrar
        GridBagConstraints cBtn = new GridBagConstraints();
        cBtn.insets = new Insets(8, 10, 20, 10);
        cBtn.gridx = 0;
        cBtn.gridy = y;
        cBtn.gridwidth = 2;
        cBtn.anchor = GridBagConstraints.CENTER;
        JButton cerrarBtn = new JButton("Cerrar");
        cerrarBtn.addActionListener(e -> dispose());
        contentPanel.add(cerrarBtn, cBtn);

        // Contenedor con scroll para mostrar todos los campos
        JScrollPane scroll = new JScrollPane(contentPanel);
        scroll.setPreferredSize(new Dimension(640, 360));
        scroll.setBorder(null);
        setContentPane(scroll);
    }

    private int agregarCampo(JPanel panel, int y, String etiqueta, String valor) {
        GridBagConstraints c1 = new GridBagConstraints();
        c1.insets = new Insets(8, 10, 8, 10);
        c1.gridx = 0;
        c1.gridy = y;
        c1.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel(etiqueta + ":"), c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.insets = new Insets(8, 10, 8, 10);
        c2.gridx = 1;
        c2.gridy = y;
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 1.0;

        JTextField campo = new JTextField(valor);
        panel.add(campo, c2);

        return y + 1;
    }
}
