package ACP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class Login extends JFrame {
    private static final long serialVersionUID = 1L;
    private JComboBox<String> usuariosCombo;
    private JButton loginButton;
    private JPasswordField txtPassword;
    private JCheckBox recordarUsuarioCheck;

    private final String ARCHIVO_RECORDADO = "recordado.txt";

    public Login() {
        setTitle("ZManager 2.0 - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 400);
        setLocationRelativeTo(null);
        setResizable(false);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png"));
        setIconImage(icon);

        // Panel principal
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        getContentPane().add(mainPanel);

        // Formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension campoTam = new Dimension(320, 35);

        // Logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel logoLabel = new JLabel(new ImageIcon(
            new ImageIcon(getClass().getResource("/LogoPNGVertical.png"))
                .getImage().getScaledInstance(130, 90, Image.SCALE_SMOOTH)));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(logoLabel, gbc);

        // Usuario label
        gbc.gridy++;
        gbc.gridwidth = 2;
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(usuarioLabel, gbc);

        // Usuario combo
        gbc.gridy++;
        usuariosCombo = new JComboBox<>();
        usuariosCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usuariosCombo.setPreferredSize(campoTam);
        formPanel.add(usuariosCombo, gbc);

        // Contraseña label
        gbc.gridy++;
        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        formPanel.add(passwordLabel, gbc);

        // Contraseña campo
        gbc.gridy++;
        txtPassword = new JPasswordField("Contraseña");
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        txtPassword.setEchoChar((char) 0);
        txtPassword.setPreferredSize(campoTam);
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("Contraseña")) {
                    txtPassword.setText("");
                    txtPassword.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).isEmpty()) {
                    txtPassword.setText("Contraseña");
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
        txtPassword.addActionListener(e -> autenticarUsuario());
        formPanel.add(txtPassword, gbc);

        // Checkbox
        gbc.gridy++;
        recordarUsuarioCheck = new JCheckBox("Recordar usuario");
        recordarUsuarioCheck.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        recordarUsuarioCheck.setBackground(Color.WHITE);
        recordarUsuarioCheck.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(recordarUsuarioCheck, gbc);

     // Botón ingresar en panel independiente para evitar afectar el layout
        gbc.gridy++;
        JPanel botonPanel = new JPanel();
        botonPanel.setBackground(Color.WHITE);
        botonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0)); // evita márgenes

        loginButton = new RoundedButton("Ingresar", new ImageIcon(getClass().getResource("/login.png")));
        loginButton.setPreferredSize(new Dimension(150, 40)); // tamaño libre
        botonPanel.add(loginButton);

        formPanel.add(botonPanel, gbc);


        // Añadir panel al principal
        gbc.gridy = 0;
        gbc.gridx = 0;
        mainPanel.add(formPanel, gbc);

        // Eventos
        loginButton.addActionListener(e -> autenticarUsuario());

        // Cargar datos
        cargarUsuariosDesdeSistema();
        cargarUsuarioRecordado();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    public String getUsuarioSeleccionado() {
        String seleccionado = (String) usuariosCombo.getSelectedItem();
        if (seleccionado == null || seleccionado.trim().isEmpty() || seleccionado.equals("Selecciona usuario...")) {
            return null;
        }
        return seleccionado.trim();
    }

    public String getPassword() {
        String pass = String.valueOf(txtPassword.getPassword());
        return pass.equals("Contraseña") ? "" : pass;
    }

    private void autenticarUsuario() {
        String usuarioInput = getUsuarioSeleccionado();
        String password = getPassword();

        if (usuarioInput == null || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debes ingresar usuario y contraseña.");
            return;
        }

        Usuario usuario = UsuarioDAO.obtenerUsuarioPorNombre(usuarioInput);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "El usuario no existe.");
            return;
        }

        String hashIngresado = Usuario.hashSHA256(password);
        if (!usuario.getClaveHash().equalsIgnoreCase(hashIngresado)) {
            JOptionPane.showMessageDialog(this, "Contraseña incorrecta.");
            return;
        }

        if (!usuario.isActivo()) {
            JOptionPane.showMessageDialog(this, "Usuario inactivo.");
            return;
        }

        Session.usuario = usuario.getUsuario();
        Session.perfil = usuario.getPerfil();

        if (recordarUsuarioCheck.isSelected()) {
            guardarUsuarioRecordado(usuario.getUsuario());
        } else {
            eliminarUsuarioRecordado();
        }

        if ("Vendedor".equalsIgnoreCase(Session.perfil)) {
            Ventas.mostrar();
        } else {
            Almacen.mostrar();
        }
        dispose();
    }

    private void guardarUsuarioRecordado(String usuario) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_RECORDADO))) {
            writer.write(usuario);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarUsuarioRecordado() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_RECORDADO))) {
            String recordado = reader.readLine();
            if (recordado != null) {
                for (int i = 0; i < usuariosCombo.getItemCount(); i++) {
                    if (usuariosCombo.getItemAt(i).equals(recordado)) {
                        usuariosCombo.setSelectedItem(recordado);
                        recordarUsuarioCheck.setSelected(true);
                        break;
                    }
                }
            }
        } catch (IOException ignored) {
        }
    }

    private void cargarUsuariosDesdeSistema() {
        usuariosCombo.removeAllItems();
        usuariosCombo.addItem("Selecciona usuario...");

        List<Usuario> usuarios = UsuarioDAO.obtenerUsuarios();
        if (usuarios == null || usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay usuarios registrados. Crea uno nuevo.");
            Perfiles.mostrar(this);
            usuarios = UsuarioDAO.obtenerUsuarios();
        }

        for (Usuario u : usuarios) {
            if (u.isActivo()) {
                usuariosCombo.addItem(u.getUsuario());
            }
        }
    }

    private void eliminarUsuarioRecordado() {
        File archivo = new File(ARCHIVO_RECORDADO);
        if (archivo.exists()) archivo.delete();
    }

    class RoundedButton extends JButton {
        public RoundedButton(String text, Icon icon) {
            super(text, icon);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(Color.WHITE);
            setBackground(new Color(0x0077B6));
            setFont(new Font("Segoe UI", Font.BOLD, 13));
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // compacta el botón
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }
    }
}
