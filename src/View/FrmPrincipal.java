package View;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ConexionDB;

public class FrmPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public PanelEntrar panelEntrar;
	public PanelDetalle panelDetalle;
	public PanelResumen panelResumen;

    private MenuBar menuBar;
    private JPanel contentPane;
    
    private Connection conn;
    

    public FrmPrincipal() {
    	
    	try {
			conn =ConexionDB.obtenerConexion();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(240, 240, 450, 300);
        setResizable(false);
        
        setTitle("Portal de calificaciones");
        setSize(550, 350);
        
        menuBar = new MenuBar();
        setJMenuBar(menuBar);
       
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout()); 
        setContentPane(contentPane); 

        // Inicializar paneles
        panelEntrar = new PanelEntrar(conn);
        //panelDetalle = new PanelDetalle(conn);
        //panelResumen = new PanelResumen(conn);
        
    
        // Añadir el panel por defecto
        contentPane.add(panelEntrar, BorderLayout.CENTER);
        
        initListeners();
        
        setVisible(true);
    }

    private void initListeners() {
        menuBar.entrarItem.addActionListener(e -> showPanel(panelEntrar));
        menuBar.salirItem.addActionListener(e -> cerrarSesion());
        //menuBar.detalleItem.addActionListener(e -> showPanel(panelDetalle));
        //menuBar.resumenItem.addActionListener(e -> showPanel(panelResumen));
        menuBar.acercaDeItem.addActionListener(e -> {
            AcercaDe acercaDeDialog = new AcercaDe(this);
            acercaDeDialog.setVisible(true);
        });
    }
    
	private void showPanel(JPanel panel) {
        contentPane.removeAll();
        contentPane.add(panel, BorderLayout.CENTER);
        contentPane.revalidate();
        contentPane.repaint();
    }

    
    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Sesión cerrada.", "Información", JOptionPane.INFORMATION_MESSAGE);
            menuBar.desactivarMenu();
        }
    }



}
