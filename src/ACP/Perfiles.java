package ACP;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;

public class Perfiles extends JDialog {
    public Perfiles(JFrame parent) {
        super(parent, "Perfiles de usuario", true);
        setSize(760, 310);
        setLocationRelativeTo(parent);
        
        // Carga la imagen desde el classpath
        Image icon = Toolkit.getDefaultToolkit()
                           .getImage(getClass().getResource("/LOGOZM.png"));
        // Fija el icono de la ventana
        setIconImage(icon);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Perfiles de usuario", JLabel.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(title, BorderLayout.NORTH);

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0};
        JPanel content = new JPanel(gbl);
        content.setBorder(new LineBorder(Color.GRAY));

        Insets insets = new Insets(5, 5, 5, 5);

        JComboBox<String> cbUsuarios = new JComboBox<>(new String[]{" - "});
        JTextField txtVendedorNum = new JTextField("0");
        JTextField txtNombre = new JTextField();
        JComboBox<String> cbPerfil = new JComboBox<>(new String[]{
            "Administrador", "Gerente", "Contador", "Jefe", "Supervisor",
            "Asistente", "Vendedor", "Cajera", "Empacador", "Telefonista"
        });
        cbPerfil.setSelectedItem("Administrador");
        JTextField txtApellidoPaterno = new JTextField();
        JTextField txtApellidoMaterno = new JTextField();
        JTextField txtEmail = new JTextField();

        // Fila 0
        content.add(new JLabel("Usuario:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(cbUsuarios, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(new JLabel("Vendedor Num:"), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtVendedorNum, new GridBagConstraints(5, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));

        // Fila 1
        content.add(new JLabel("Nombre(s):"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtNombre, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(new JLabel("Perfil:"), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(cbPerfil, new GridBagConstraints(5, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));

        // Fila 2
        content.add(new JLabel("Apellido Paterno:"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtApellidoPaterno, new GridBagConstraints(1, 2, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));

        // Fila 3
        content.add(new JLabel("Apellido Materno:"), new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtApellidoMaterno, new GridBagConstraints(1, 3, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));

        // Fila 4
        content.add(new JLabel("Email:"), new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtEmail, new GridBagConstraints(1, 4, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));

        panel.add(content, BorderLayout.CENTER);
                        JLabel label_1 = new JLabel("Clave:");
                        content.add(label_1, new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
                        JTextField txtClave = new JTextField();
                        content.add(txtClave, new GridBagConstraints(1, 5, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
                        JLabel label = new JLabel("Apodo:");
                        content.add(label, new GridBagConstraints(4, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
                        JTextField txtApodo = new JTextField();
                        content.add(txtApodo, new GridBagConstraints(5, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, new Insets(5, 5, 5, 0), 0, 0));
                        JCheckBox chkActivo = new JCheckBox("Activo");
                                // Fila 5
                                content.add(chkActivo, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(5, 5, 5, 5), 0, 0));
                                // Botón
                                JButton btnActualizar = new JButton("Actualizar Usuario");
                                content.add(btnActualizar, new GridBagConstraints(5, 6, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(5, 5, 5, 0), 0, 0));
                
                        btnActualizar.addActionListener(e -> {
                            JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente");
                            dispose();
                        });
        getContentPane().add(panel);
    }

    public static void mostrar(JFrame parent) {
        Perfiles dialog = new Perfiles(parent);
        dialog.setVisible(true);
    }
}
