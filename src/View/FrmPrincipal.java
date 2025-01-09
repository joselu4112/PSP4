package View;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Controller.ConexionDB;
import Controller.ControladorDetalle;
import Controller.ControladorResumen;
import Controller.ControladorValidar;

public class FrmPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;
    
    public PanelEntrar panelEntrar;
    public PanelDetalle panelDetalle;
    public PanelResumen panelResumen;

    public MenuBar menuBar;
    private JPanel contentPane;
    
    private Connection conn;
    
    private int numAlum;
    

    public FrmPrincipal() {
        try {
            conn = ConexionDB.obtenerConexion();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error al obtener la conexión con la base de datos ",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE);
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
        panelEntrar = new PanelEntrar(conn, this);
        
        ControladorValidar ctrValidar = new ControladorValidar(conn);
        numAlum = ctrValidar.getNumUsuario();
        
        // Añadir el panel por defecto
        contentPane.add(panelEntrar, BorderLayout.CENTER);
        
        initListeners();
        
//        try {
//            ConexionDB.cerrarConexion(conn);
//        } catch (SQLException e) {
//            JOptionPane.showMessageDialog(this, 
//                "Error al cerrar la conexión con la base de datos",
//                "Error de conexión",
//                JOptionPane.ERROR_MESSAGE);
//        }
        
        setVisible(true);
    }

    private void initListeners() {
        menuBar.entrarItem.addActionListener(e -> showPanel(panelEntrar));
        menuBar.salirItem.addActionListener(e -> cerrarSesion());
        menuBar.detalleItem.addActionListener(e -> showPanel(panelDetalle));
        menuBar.resumenItem.addActionListener(e -> showPanel(panelResumen));
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
    
    public void cambiarPanel() {
        Statement stmDetalle = null;
        try {
            stmDetalle = ConexionDB.obtenerStatementDetalle(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error de conexión con la base de datos",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE);
        }
        
        ControladorDetalle ctrDetalle = new ControladorDetalle(stmDetalle);
        panelDetalle = new PanelDetalle(ctrDetalle, numAlum);
        
        Statement stmResumen = null;
        try {
            stmResumen = ConexionDB.obtenerStatementResumen(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
            	"Error de conexión con la base de datos",
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE);
        }
        
        ControladorResumen ctrResumen = new ControladorResumen(stmResumen);
        panelResumen = new PanelResumen(ctrResumen, numAlum);
        
        showPanel(panelResumen);
    }

    private void cerrarSesion() {
        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Sesión cerrada.", 
                "Información", 
                JOptionPane.INFORMATION_MESSAGE);
            menuBar.desactivarMenu();
            showPanel(panelEntrar);
        }
    }
}
