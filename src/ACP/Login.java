package ACP;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;

public class Login extends JFrame {
    private JComboBox<String> usuariosCombo;
    private JButton loginButton;
    private JPasswordField txtPassword;
    private JCheckBox recordarUsuarioCheck;

    private final String ARCHIVO_RECORDADO = "recordado.txt";

    public Login() {
        setTitle("ZManager 2.0 - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(405, 301);
        setLocationRelativeTo(null);
        setResizable(false);

        // Icono
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
        mainPanel.add(usuariosCombo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        cargarUsuarios(); //

        txtPassword = new JPasswordField("Contraseña");
        txtPassword.setBackground(Color.WHITE);
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        txtPassword.setEchoChar((char) 0);
        txtPassword.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (String.valueOf(txtPassword.getPassword()).equals("Contraseña")) {
                    txtPassword.setText("");
                    txtPassword.setEchoChar('\u2022');
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
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        loginButton = new JButton("Ingresar");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setBackground(new Color(0x006C9D));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setPreferredSize(new Dimension(100, 35));
        mainPanel.add(loginButton);

        getContentPane().add(mainPanel);

        cargarUsuarioRecordado(); //Cargar_usuario_recordado_(si_existe)

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    private void cargarUsuarios() {
        usuariosCombo.addItem("Selecciona usuario...");
        String[] usuarios = {"Juan", "Pedro", "María", "Pepe"};
        for (String usuario : usuarios) {
            usuariosCombo.addItem(usuario);
        }
    }

    public String getUsuarioSeleccionado() {
        String seleccionado = (String) usuariosCombo.getSelectedItem();
        return seleccionado != null && !seleccionado.equals("Selecciona usuario...") ? seleccionado : null;
    }

    public String getPassword() {
        String pass = String.valueOf(txtPassword.getPassword());
        return pass.equals("Contraseña") ? "" : pass;
    }

    public void addLoginListener(java.awt.event.ActionListener listener) {
        loginButton.addActionListener(e -> {
            if (recordarUsuarioCheck.isSelected()) {
                guardarUsuarioRecordado(getUsuarioSeleccionado());
            } else {
                eliminarUsuarioRecordado();
            }
            listener.actionPerformed(e);
        });
    }

    private void guardarUsuarioRecordado(String usuario) {
        if (usuario != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ARCHIVO_RECORDADO))) {
                writer.write(usuario);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarUsuarioRecordado() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ARCHIVO_RECORDADO))) {
            String recordado = reader.readLine();
            if (recordado != null) {
                for (int i = 0; i < usuariosCombo.getItemCount(); i++) {
                    if (usuariosCombo.getItemAt(i).equals(recordado)) {
                        usuariosCombo.setSelectedIndex(i);
                        recordarUsuarioCheck.setSelected(true);
                        break;
                    }
                }
            }
        } catch (IOException ignored) {}
    }

    private void eliminarUsuarioRecordado() {
        File archivo = new File(ARCHIVO_RECORDADO);
        if (archivo.exists()) archivo.delete();
    }
}
