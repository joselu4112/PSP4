package View;

import java.awt.Dimension;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Controller.ControladorDetalle;
import Model.Asignatura;
import Model.NotaInvalidaException;

public class PanelDetalle extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel lblNombre, lblNota;
    private JTextField txtNota;
    private JButton btnGuardar, btnAnterior, btnSiguiente, btnPrimero, btnUltimo;

    private ControladorDetalle controlador;
    private List<Asignatura> asignaturas;
    private int currentIndex = 0;

    public PanelDetalle(ControladorDetalle controlador, int aluNumero) {
        this.controlador = controlador;

        // Definir tamaño del panel
        setPreferredSize(new Dimension(500, 300));
        setLayout(null); // Absolute layout

        // Etiqueta Nombre
        lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 200, 30); // Posición y tamaño
        add(lblNombre);

        // Etiqueta Nota
        lblNota = new JLabel("Nota:");
        lblNota.setBounds(20, 70, 200, 30);
        add(lblNota);

        // Campo de texto para Nota
        txtNota = new JTextField();
        txtNota.setBounds(80, 70, 100, 30);
        add(txtNota);

        // Botón Guardar
        btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(200, 200, 100, 30);
        add(btnGuardar);

        // Botón Anterior
        btnAnterior = new JButton("Anterior");
        btnAnterior.setBounds(80, 200, 100, 30);
        add(btnAnterior);

        // Botón Siguiente
        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setBounds(320, 200, 100, 30);
        add(btnSiguiente);

        // Botón Primero
        btnPrimero = new JButton("Primero");
        btnPrimero.setBounds(80, 230, 100, 30);
        add(btnPrimero);

        // Botón Último
        btnUltimo = new JButton("Último");
        btnUltimo.setBounds(320, 230, 100, 30);
        add(btnUltimo);

        // Cargar asignaturas del alumno
        cargarAsignaturas(aluNumero);

        // Listeners de los botones
        btnAnterior.addActionListener(e -> mostrarAsignaturaAnterior());
        btnSiguiente.addActionListener(e -> mostrarAsignaturaSiguiente());
        btnPrimero.addActionListener(e -> mostrarPrimeraAsignatura());
        btnUltimo.addActionListener(e -> mostrarUltimaAsignatura());
        btnGuardar.addActionListener(e -> guardarNota());
    }

    private void cargarAsignaturas(int aluNumero) {
        try {
            asignaturas = controlador.obtenerAsignaturasDeAlumno(aluNumero);
            if (!asignaturas.isEmpty()) {
                currentIndex = 0;
                mostrarAsignatura(asignaturas.get(currentIndex));
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron asignaturas para el alumno.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar asignaturas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NotaInvalidaException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarAsignatura(Asignatura asignatura) {
        lblNombre.setText("Nombre: " + asignatura.getNombre());
        txtNota.setText(String.valueOf(asignatura.getNota()));
    }

    private void mostrarAsignaturaAnterior() {
        if (currentIndex > 0) {
            currentIndex--;
            mostrarAsignatura(asignaturas.get(currentIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No hay asignaturas anteriores.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarAsignaturaSiguiente() {
        if (currentIndex < asignaturas.size() - 1) {
            currentIndex++;
            mostrarAsignatura(asignaturas.get(currentIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No hay más asignaturas.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarPrimeraAsignatura() {
        if (!asignaturas.isEmpty()) {
            currentIndex = 0;
            mostrarAsignatura(asignaturas.get(currentIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No hay asignaturas para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void mostrarUltimaAsignatura() {
        if (!asignaturas.isEmpty()) {
            currentIndex = asignaturas.size() - 1;
            mostrarAsignatura(asignaturas.get(currentIndex));
        } else {
            JOptionPane.showMessageDialog(this, "No hay asignaturas para mostrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void guardarNota() {
        try {
            float nuevaNota = Float.parseFloat(txtNota.getText().trim());
          
            Asignatura asignatura = asignaturas.get(currentIndex);
            asignatura.setNota(nuevaNota);
            controlador.actualizarNota(asignatura);

            JOptionPane.showMessageDialog(this, "Nota actualizada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una nota válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la nota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NotaInvalidaException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


