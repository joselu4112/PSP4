package View;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AcercaDe extends JDialog {

    public AcercaDe(JFrame parent) {
        super(parent, "Acerca de", true); // Modal JDialog
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        // Información del programa
        JLabel lblInfo = new JLabel(
                "<html><center>" +
                "<h2>Portal de Calificaciones</h2>" +
                "<p>Versión: 1.0</p>" +
                "<p>Autor: Juan Pérez</p>" +
                "<p>Fecha: Enero 2025</p>" +
                "</center></html>"
        );
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblInfo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(lblInfo, BorderLayout.CENTER);

        // Botón para cerrar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> dispose());
        JPanel btnPanel = new JPanel();
        btnPanel.add(btnAceptar);
        panel.add(btnPanel, BorderLayout.SOUTH);
    }
}
