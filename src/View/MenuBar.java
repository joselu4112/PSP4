package View;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar {
    private JMenu validarItem;
    public JMenuItem entrarItem;
    public JMenuItem salirItem;
    private JMenu visualizarItem;
    public JMenuItem detalleItem;
    public JMenuItem resumenItem;
    public JMenuItem acercaDeItem; // Cambiado de JMenu a JMenuItem

    public MenuBar() {
        // Menú Validar
        validarItem = new JMenu("Validar");
        add(validarItem);

        entrarItem = new JMenuItem("Entrar");
        validarItem.add(entrarItem);

        salirItem = new JMenuItem("Salir");
        validarItem.add(salirItem);

        // Menú Visualizar
        visualizarItem = new JMenu("Visualizar");
        add(visualizarItem);

        detalleItem = new JMenuItem("Detalle");
        visualizarItem.add(detalleItem);

        resumenItem = new JMenuItem("Resumen");
        visualizarItem.add(resumenItem);

        // Menú Acerca de
        acercaDeItem = new JMenuItem("Acerca de"); // Crear como JMenuItem
        add(acercaDeItem); // Añadirlo directamente a la barra de menú

        desactivarMenu();
    }

    public void activarMenu() {
        salirItem.setEnabled(true);
        detalleItem.setEnabled(true);
        resumenItem.setEnabled(true);
        acercaDeItem.setEnabled(true);
        entrarItem.setEnabled(false);
    }

    public void desactivarMenu() {
        salirItem.setEnabled(false);
        detalleItem.setEnabled(false);
        resumenItem.setEnabled(false);
        acercaDeItem.setEnabled(false);
        entrarItem.setEnabled(true);
    }
}


