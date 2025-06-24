package ACP;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class ConexionesBD extends JDialog {

    public ConexionesBD(JFrame parent) {
        super(parent, "Conexiones de Base de Datos", true);
        setSize(784, 495);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Icono
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));
        setIconImage(icon);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Conexión a Base de Datos", JLabel.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridBagLayout());
        content.setBorder(new LineBorder(Color.GRAY));
        content.setBackground(Color.WHITE);

        Insets insets = new Insets(5, 10, 5, 10);

        // Campos
        JTextField txtConnectionName = new JTextField();
        JTextField txtDriverName = new JTextField();
        JTextField txtLibraryName = new JTextField();
        JTextField txtVendorLibrary = new JTextField();
        JTextField txtDriverFunction = new JTextField();
        JTextField txtHostName = new JTextField();
        JTextField txtPort = new JTextField();
        JTextField txtDatabase = new JTextField();
        JTextField txtUserName = new JTextField();
        JPasswordField txtPassword = new JPasswordField();

        // Helper para añadir campos
        addField(content, 0, "Connection Name:", txtConnectionName, insets);
        addField(content, 1, "Driver Name:", txtDriverName, insets);
        addField(content, 2, "Library Name:", txtLibraryName, insets);
        addField(content, 3, "Vendor Library:", txtVendorLibrary, insets);
        addField(content, 4, "Driver Function:", txtDriverFunction, insets);
        addField(content, 5, "Host Name:", txtHostName, insets);
        addField(content, 6, "Port:", txtPort, insets);
        addField(content, 7, "Database:", txtDatabase, insets);
        addField(content, 8, "User Name:", txtUserName, insets);
        addField(content, 9, "Password:", txtPassword, insets);

        // Botón Guardar
        JButton guardarBtn = new JButton("Guardar Conexión");
        guardarBtn.setBackground(new Color(0, 123, 255));
        guardarBtn.setForeground(new Color(0, 0, 0));
        guardarBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        guardarBtn.setFocusPainted(false);
        guardarBtn.setPreferredSize(new Dimension(180, 35));

        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.gridx = 0;
        gbcBtn.gridy = 10;
        gbcBtn.gridwidth = 2;
        gbcBtn.anchor = GridBagConstraints.CENTER;
        gbcBtn.insets = new Insets(20, 10, 10, 10);
        content.add(guardarBtn, gbcBtn);

        guardarBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Conexión guardada correctamente.");
            dispose();
        });

        panel.add(content, BorderLayout.CENTER);
        getContentPane().add(panel);
    }

    private void addField(JPanel panel, int y, String labelText, JComponent field, Insets insets) {
        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = 0;
        gbcLabel.gridy = y;
        gbcLabel.anchor = GridBagConstraints.EAST;
        gbcLabel.insets = insets;
        panel.add(new JLabel(labelText), gbcLabel);

        GridBagConstraints gbcField = new GridBagConstraints();
        gbcField.gridx = 1;
        gbcField.gridy = y;
        gbcField.weightx = 1.0;
        gbcField.fill = GridBagConstraints.HORIZONTAL;
        gbcField.insets = insets;
        panel.add(field, gbcField);
    }

    public static void mostrar(JFrame parent) {
        ConexionesBD dialog = new ConexionesBD(parent);
        dialog.setVisible(true);
    }
    
    public ConexionesBD() {
        this((JFrame) null); // Llama al constructor principal
    }

    
}
