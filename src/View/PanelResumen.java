package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Controller.ControladorImagen;
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
    private Connection conn;

    public PanelResumen(Connection conn, ControladorResumen controlador, int aluNumero) {
        this.controlador = controlador;
        this.aluNumero = aluNumero;
        this.conn = conn;

        setLayout(new BorderLayout());
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        // Panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
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

        // Tabla
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Nota"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tableAsignaturas = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableAsignaturas);

        // Botón calcular
        btnCalcular = new JButton("Calcular");
        btnCalcular.addActionListener(e -> calcularNotaMedia());

        JPanel panelInferior = new JPanel();
        panelInferior.add(btnCalcular);

        // Añadir paneles
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }

    private void cargarDatos() {
        try {
            alumno = controlador.obtenerAlumno(aluNumero);
            if (alumno != null) {
                txtUsuario.setText(alumno.getUsuario());
                txtNotaMedia.setText(String.valueOf(alumno.getNotaMedia()));

                // Manejo de imagen
                if (alumno.getImagen() != null) {
                    mostrarImagen(alumno.getImagen());
                } else {
                    cargarImagenPorDefecto();
                }

                lblImagen.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cambiarImagenAlumno();
                    }
                });

                cargarAsignaturas();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el alumno.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos del alumno: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarImagen(Blob imagenBlob) {
        try {
            ImageIcon icon = new ImageIcon(imagenBlob.getBytes(1, (int) imagenBlob.length()));
            Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(scaledImage));
        } catch (SQLException ex) {
            cargarImagenPorDefecto();
        }
    }

    private void cargarImagenPorDefecto() {
        try {
            String rutaImagenPorDefecto = "C:\\usuario.jpg";
            File archivoImagen = new File(rutaImagenPorDefecto);

            if (archivoImagen.exists()) {
                ImageIcon icon = new ImageIcon(rutaImagenPorDefecto);
                Image scaledImage = icon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(scaledImage));
            } else {
                lblImagen.setText("No hay imagen");
            }
        } catch (Exception ex) {
            lblImagen.setText("No hay imagen");
        }
    }

    private void cambiarImagenAlumno() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes JPG", "jpg"));
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();

            if (archivoSeleccionado != null && archivoSeleccionado.exists()) {
                try {
                    // Usar ControladorImagen para convertir y guardar la imagen en la base de datos
                    ControladorImagen.actualizarImagenAlumno(aluNumero, archivoSeleccionado, conn);

                    // Recuperar la nueva imagen para mostrarla
                    Blob nuevaImagen = ControladorImagen.obtenerImagenAlumno(aluNumero, conn);
                    alumno.setImagen(nuevaImagen);

                    // Mostrar la nueva imagen en el JLabel
                    mostrarImagen(nuevaImagen);

                    JOptionPane.showMessageDialog(this, "Imagen actualizada correctamente y guardada en la base de datos.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la imagen: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Archivo no válido o inexistente.", "Error", JOptionPane.WARNING_MESSAGE);
            }
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


