package View;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import Model.Asignatura;
import Model.NotaInvalidaException;
import Controller.ControladorDetalle;

public class PanelDetalle extends JPanel {
    private static final long serialVersionUID = 1L;

    private JLabel lblNombre, lblNota;
    private JTextField txtNota;
    private JButton btnGuardar, btnAnterior, btnSiguiente;

    private ControladorDetalle controlador;
    private List<Asignatura> asignaturas;
    private int currentIndex = 0;

    public PanelDetalle(ControladorDetalle controlador, int aluNumero) {
        this.controlador = controlador;

        setLayout(new BorderLayout());

        // Panel superior para mostrar detalles
        JPanel panelDetalles = new JPanel(new GridLayout(2, 2));
        lblNombre = new JLabel("Nombre:");
        lblNota = new JLabel("Nota:");
        txtNota = new JTextField();

        panelDetalles.add(lblNombre);
        panelDetalles.add(new JLabel()); // Placeholder
        panelDetalles.add(lblNota);
        panelDetalles.add(txtNota);

        add(panelDetalles, BorderLayout.CENTER);

        // Panel inferior para botones
        JPanel panelBotones = new JPanel();
        btnGuardar = new JButton("Guardar");
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");

        panelBotones.add(btnAnterior);
        panelBotones.add(btnGuardar);
        panelBotones.add(btnSiguiente);

        add(panelBotones, BorderLayout.SOUTH);

        // Cargar asignaturas del alumno
        cargarAsignaturas(aluNumero);

        // Listeners de los botones
        btnAnterior.addActionListener(e -> mostrarAsignaturaAnterior());
        btnSiguiente.addActionListener(e -> mostrarAsignaturaSiguiente());
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

    private void guardarNota() {
        try {
            float nuevaNota = Float.parseFloat(txtNota.getText().trim());
            if (nuevaNota < 0 || nuevaNota > 10) {
                JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 10.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Asignatura asignatura = asignaturas.get(currentIndex);
            try {
				asignatura.setNota(nuevaNota);
			} catch (NotaInvalidaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // Actualizar la nota en el objeto
            controlador.actualizarNota(asignatura); // Guardar en la BD

            JOptionPane.showMessageDialog(this, "Nota actualizada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese una nota válida.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar la nota: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}


