package View;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controller.ControladorValidar;

public class PanelEntrar extends JPanel {
    private JTextField usuarioField;
    private JPasswordField contrasenaField;
    private JButton btnIniciarSesion;
    private JButton btnCancelar;

    private ControladorValidar controlador;
    private MenuBar menuBar;
    
    private FrmPrincipal frmPrincipal;


    public PanelEntrar(Connection conexion, FrmPrincipal frmPrincipal) {
        this.controlador = new ControladorValidar(conexion);
        this.frmPrincipal = frmPrincipal;
        menuBar = new MenuBar();
        setLayout(null);

        // Etiqueta Usuario
        JLabel usuarioLabel = new JLabel("Usuario:");
        usuarioField = new JTextField(20);
        add(usuarioLabel);
        add(usuarioField);
        usuarioLabel.setBounds(20, 20, 100, 25);
        usuarioField.setBounds(130, 20, 320, 25);

        // Etiqueta Contraseña
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        contrasenaField = new JPasswordField(20);
        add(contrasenaLabel);
        add(contrasenaField);
        contrasenaLabel.setBounds(20, 60, 100, 25);
        contrasenaField.setBounds(130, 60, 320, 25);

        // Botón Iniciar Sesión
        btnIniciarSesion = new JButton("Iniciar Sesión");
        add(btnIniciarSesion);
        btnIniciarSesion.setBounds(130, 100, 150, 30);

        // Botón Cancelar
        btnCancelar = new JButton("Cancelar");
        add(btnCancelar);
        btnCancelar.setBounds(300, 100, 150, 30);

        initListeners();
    }

    private void initListeners() {
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                autenticarUsuario();
            }
        });

        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });
    }

    private void autenticarUsuario() {
        String usuario = usuarioField.getText().trim();
        String contrasena = new String(contrasenaField.getPassword()).trim();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, complete ambos campos.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean esValido = controlador.validarUsuario(usuario, contrasena);

            if (esValido) {
                JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                menuBar.activarMenu();
                frmPrincipal.cambiarPanel(); 
                
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al validar las credenciales: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void limpiarCampos() {
        usuarioField.setText("");
        contrasenaField.setText("");
    }
    
    
}



