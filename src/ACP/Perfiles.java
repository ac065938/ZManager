package ACP;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Perfiles extends JDialog {
    private JTable tablaUsuarios;
    private DefaultTableModel modeloTabla;

    private JTextField txtUsuario;
    private JTextField txtVendedorNum;
    private JTextField txtNombre;
    private JComboBox<String> cbPerfil;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtEmail;
    private JTextField txtClave;
    private JTextField txtApodo;
    private JCheckBox chkActivo;
    private JTabbedPane tabs;

    public Perfiles(JFrame parent) {
        super(parent, "Perfiles de usuario", true);
        setSize(800, 400);
        setLocationRelativeTo(parent);

        Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/LOGOZM.png"));
        setIconImage(icon);

        tabs = new JTabbedPane();

        JPanel formularioPanel = crearFormularioPanel();
        JPanel listaPanel = crearListaUsuariosPanel();

        tabs.addTab("Formulario", formularioPanel);
        tabs.addTab("Usuarios Registrados", listaPanel);

        getContentPane().add(tabs);
    }
    
    private void limpiarFormulario(JTextField txtUsuario, JTextField txtVendedorNum, JTextField txtNombre,
            JTextField txtApellidoPaterno, JTextField txtApellidoMaterno, JTextField txtEmail,
            JTextField txtClave, JTextField txtApodo, JCheckBox chkActivo, JComboBox<String> cbPerfil) {
txtUsuario.setText("");
txtVendedorNum.setText("0");
txtNombre.setText("");
txtApellidoPaterno.setText("");
txtApellidoMaterno.setText("");
txtEmail.setText("");
txtClave.setText("");
txtApodo.setText("");
chkActivo.setSelected(false);
cbPerfil.setSelectedItem("Administrador");
}


    private JPanel crearFormularioPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0, 1.0};
        JPanel content = new JPanel(gbl);
        content.setBorder(new LineBorder(Color.GRAY));

        Insets insets = new Insets(5, 5, 5, 5);

        txtUsuario = new JTextField();
        txtVendedorNum = new JTextField("0");
        txtNombre = new JTextField();
        cbPerfil = new JComboBox<>(new String[]{
                "Administrador", "Gerente", "Contador", "Jefe", "Supervisor",
                "Asistente", "Vendedor", "Cajera", "Empacador", "Telefonista"
        });
        cbPerfil.setSelectedItem("Administrador");
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtEmail = new JTextField();
        txtClave = new JTextField();
        txtApodo = new JTextField();
        chkActivo = new JCheckBox("Activo");

        content.add(new JLabel("Usuario:"), new GridBagConstraints(0, 0, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtUsuario, new GridBagConstraints(1, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(new JLabel("Vendedor Num:"), new GridBagConstraints(2, 0, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtVendedorNum, new GridBagConstraints(5, 0, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(new JLabel("Nombre(s):"), new GridBagConstraints(0, 1, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtNombre, new GridBagConstraints(1, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(new JLabel("Perfil:"), new GridBagConstraints(2, 1, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(cbPerfil, new GridBagConstraints(5, 1, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(new JLabel("Apellido Paterno:"), new GridBagConstraints(0, 2, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtApellidoPaterno, new GridBagConstraints(1, 2, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(new JLabel("Apellido Materno:"), new GridBagConstraints(0, 3, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtApellidoMaterno, new GridBagConstraints(1, 3, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(new JLabel("Email:"), new GridBagConstraints(0, 4, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtEmail, new GridBagConstraints(1, 4, 5, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(new JLabel("Clave:"), new GridBagConstraints(0, 5, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
        content.add(txtClave, new GridBagConstraints(1, 5, 2, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(new JLabel("Apodo:"), new GridBagConstraints(4, 5, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.HORIZONTAL, insets, 0, 0));
        content.add(txtApodo, new GridBagConstraints(5, 5, 1, 1, 0, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, insets, 0, 0));

        content.add(chkActivo, new GridBagConstraints(0, 6, 1, 1, 0, 0, GridBagConstraints.WEST, GridBagConstraints.NONE, insets, 0, 0));
        JButton btnActualizar = new JButton("Actualizar Usuario");
        content.add(btnActualizar, new GridBagConstraints(5, 6, 1, 1, 0, 0, GridBagConstraints.EAST, GridBagConstraints.NONE, insets, 0, 0));

        btnActualizar.addActionListener(e -> {
            try {
                Usuario usuario = new Usuario();
                usuario.setUsuario(txtUsuario.getText().trim());
                usuario.setClaveHash(Usuario.hashSHA256(txtClave.getText()));
                usuario.setNombre(txtNombre.getText());
                usuario.setApellidoPaterno(txtApellidoPaterno.getText());
                usuario.setApellidoMaterno(txtApellidoMaterno.getText());
                usuario.setPerfil((String) cbPerfil.getSelectedItem());
                usuario.setVendedorNum(Integer.parseInt(txtVendedorNum.getText()));
                usuario.setApodo(txtApodo.getText());
                usuario.setEmail(txtEmail.getText());
                usuario.setActivo(chkActivo.isSelected());

                UsuarioDAO.guardarUsuario(usuario);

                JOptionPane.showMessageDialog(this, "Usuario guardado correctamente");
                actualizarTablaUsuarios();
                
                limpiarFormulario(txtUsuario, txtVendedorNum, txtNombre, txtApellidoPaterno, txtApellidoMaterno,
                        txtEmail, txtClave, txtApodo, chkActivo, cbPerfil);

                
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
            }
        });

        panel.add(content, BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearListaUsuariosPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        modeloTabla = new DefaultTableModel(new Object[]{"Usuario", "Nombre", "Perfil", "Activo"}, 0);
        tablaUsuarios = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tablaUsuarios);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel acciones = new JPanel();
        JButton btnEliminar = new JButton("Eliminar seleccionado");
        JButton btnEditar = new JButton("Editar seleccionado");
        acciones.add(btnEditar);
        acciones.add(btnEliminar);

        btnEliminar.addActionListener(e -> eliminarUsuarioSeleccionado());
        btnEditar.addActionListener(e -> cargarUsuarioSeleccionadoEnFormulario());

        panel.add(acciones, BorderLayout.SOUTH);
        actualizarTablaUsuarios();
        return panel;
    }

    private void actualizarTablaUsuarios() {
        modeloTabla.setRowCount(0);
        for (Usuario u : UsuarioDAO.obtenerUsuarios()) {
            modeloTabla.addRow(new Object[]{u.getUsuario(), u.getNombre(), u.getPerfil(), u.isActivo()});
        }
    }

    private void eliminarUsuarioSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila >= 0) {
            String usuario = (String) modeloTabla.getValueAt(fila, 0);

            int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de eliminar al usuario \"" + usuario + "\"?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                List<Usuario> usuarios = UsuarioDAO.obtenerUsuarios();
                usuarios.removeIf(u -> {
                    String actual = u.getUsuario();
                    return actual != null && actual.equalsIgnoreCase(usuario);
                });
                UsuarioDAO.guardarUsuarios(usuarios);
                actualizarTablaUsuarios();

                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
        }
    }

    private void cargarUsuarioSeleccionadoEnFormulario() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila >= 0) {
            String usuario = (String) modeloTabla.getValueAt(fila, 0);
            Usuario u = UsuarioDAO.obtenerUsuarioPorNombre(usuario);
            if (u != null) {
                txtUsuario.setText(u.getUsuario());
                txtNombre.setText(u.getNombre());
                txtApellidoPaterno.setText(u.getApellidoPaterno());
                txtApellidoMaterno.setText(u.getApellidoMaterno());
                txtEmail.setText(u.getEmail());
                txtClave.setText("");
                txtApodo.setText(u.getApodo());
                cbPerfil.setSelectedItem(u.getPerfil());
                txtVendedorNum.setText(String.valueOf(u.getVendedorNum()));
                chkActivo.setSelected(u.isActivo());
                tabs.setSelectedIndex(0);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
        }
    }

    public static void mostrar(JFrame parent) {
        Perfiles dialog = new Perfiles(parent);
        dialog.setVisible(true);
    }
}
