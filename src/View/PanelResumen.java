package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Controller.ControladorResumen;
import Model.Alumno;
import Model.Asignatura;

public class PanelResumen extends JPanel {
    private static final long serialVersionUID = 1L;

    private ControladorResumen controlador;
    private Alumno alumno;
    private JLabel lblImagen;
    private JTextField txtUsuario, txtNotaMedia;
    private JTable tableAsignaturas;
    private DefaultTableModel tableModel;
    private JButton btnCalcular;
    private int aluNumero;

    public PanelResumen(ControladorResumen controlador, int aluNumero) {
        this.controlador = controlador;
        this.aluNumero = aluNumero;

        setLayout(new BorderLayout());

        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        JPanel panelDatos = new JPanel(new GridLayout(2, 2));

        JLabel lblUsuario = new JLabel("Usuario:");
        txtUsuario = new JTextField();
        txtUsuario.setEditable(false);

        JLabel lblNotaMedia = new JLabel("Nota Media:");
        txtNotaMedia = new JTextField();
        txtNotaMedia.setEditable(false);

        panelDatos.add(lblUsuario);
        panelDatos.add(txtUsuario);
        panelDatos.add(lblNotaMedia);
        panelDatos.add(txtNotaMedia);

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(150, 150));

        panelSuperior.add(panelDatos, BorderLayout.CENTER);
        panelSuperior.add(lblImagen, BorderLayout.EAST);

        // Configurar JTable
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Nota"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // No editable
            }
        };
        tableAsignaturas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAsignaturas);

        // Botón Calcular
        btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(e -> calcularNotaMedia());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCalcular);

        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        cargarDatos();
    }

    private void cargarDatos() {
        try {
            alumno = controlador.obtenerAlumno(aluNumero);
            if (alumno != null) {
                txtUsuario.setText(alumno.getUsuario());
                txtNotaMedia.setText(String.valueOf(alumno.getNotaMedia()));

                // Mostrar imagen si existe
                if (alumno.getImagen() != null) {
                    ImageIcon icon = new ImageIcon(alumno.getImagen().getBytes(1, (int) alumno.getImagen().length()));
                    Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(scaledImage));
                } else {
                    lblImagen.setIcon(null);
                }

                cargarAsignaturas();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarAsignaturas() {
        try {
            List<Asignatura> asignaturas = controlador.obtenerAsignaturasDeAlumno(aluNumero);
            tableModel.setRowCount(0); // Limpiar tabla
            for (Asignatura asignatura : asignaturas) {
                tableModel.addRow(new Object[]{asignatura.getNombre(), asignatura.getNota()});
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularNotaMedia() {
        try {
            float notaMediaCalculada = controlador.calcularNotaMedia(aluNumero);
            if (alumno != null) {
                if (notaMediaCalculada != alumno.getNotaMedia()) {
                    JOptionPane.showMessageDialog(this,
                            "Nota media calculada: " + notaMediaCalculada + "\n"
                                    + "Nota media almacenada: " + alumno.getNotaMedia() + "\n"
                                    + "Actualizando nota media en la base de datos.",
                            "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    alumno.setNotaMedia(notaMediaCalculada);
                    controlador.actualizarAlumno(alumno);
                    txtNotaMedia.setText(String.valueOf(notaMediaCalculada));
                } else {
                    JOptionPane.showMessageDialog(this,
                            "La nota media calculada coincide con la almacenada.",
                            "Información", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al calcular la nota media: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


