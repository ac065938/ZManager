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
    private JButton btnNuevoUsuario;
    private JPasswordField txtPassword;
    private JCheckBox recordarUsuarioCheck;

    private final String ARCHIVO_RECORDADO = "recordado.txt";

    public Login() {
        setTitle("ZManager 2.0 - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(405, 340);
        setLocationRelativeTo(null);
        setResizable(false);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png"));
        setIconImage(icon);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        JLabel logoLabel = new JLabel();
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ImageIcon logo = new ImageIcon(Login.class.getResource("/LogoPNGVertical.png"));
        Image scaled = logo.getImage().getScaledInstance(100, 60, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaled));
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        usuariosCombo = new JComboBox<>();
        usuariosCombo.setBackground(Color.WHITE);
        usuariosCombo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usuariosCombo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        usuariosCombo.setPreferredSize(new Dimension(200, 30)); // Tamaño preferido agregado para evitar cuadro raro
        mainPanel.add(usuariosCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        txtPassword = new JPasswordField("Contraseña");
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setEchoChar((char) 0);
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("Contraseña")) {
                    txtPassword.setText("");
                    txtPassword.setEchoChar('•');
                }
            }

            public void focusLost(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).trim().isEmpty()) {
                    txtPassword.setText("Contraseña");
                    txtPassword.setEchoChar((char) 0);
                }
            }
        });
        mainPanel.add(txtPassword);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        recordarUsuarioCheck = new JCheckBox("Recordar usuario");
        recordarUsuarioCheck.setAlignmentX(Component.CENTER_ALIGNMENT);
        recordarUsuarioCheck.setBackground(Color.WHITE);
        recordarUsuarioCheck.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mainPanel.add(recordarUsuarioCheck);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        loginButton = new JButton("Ingresar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(0x006C9D));
        loginButton.setForeground(Color.BLACK);
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        btnNuevoUsuario = new JButton("Nuevo Usuario");
        btnNuevoUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNuevoUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnNuevoUsuario.setBackground(new Color(0xCCCCCC));
        btnNuevoUsuario.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(btnNuevoUsuario);

        getContentPane().add(mainPanel);

        // Importante: primero cargar todos los usuarios, luego el usuario recordado
        cargarUsuariosDesdeSistema();
        cargarUsuarioRecordado();

        loginButton.addActionListener(e -> autenticarUsuario());
        btnNuevoUsuario.addActionListener(e -> Perfiles.mostrar(this));

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

        dispose();
        Almacen.mostrar();
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
                boolean existe = false;
                for (int i = 0; i < usuariosCombo.getItemCount(); i++) {
                    if (usuariosCombo.getItemAt(i).equals(recordado)) {
                        existe = true;
                        break;
                    }
                }
                if (existe) {
                    usuariosCombo.setSelectedItem(recordado);
                    recordarUsuarioCheck.setSelected(true);
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
}
