package clienteApp.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class VistaCambioClave extends JFrame{
    private JPanel mainPanel;
    private JPasswordField claveActual;
    private JButton limpiarButton;
    private JButton aceptarButton;
    private JButton regresarButton;
    private JPasswordField ClaveNueva;
    private JPasswordField confirmacionClaveNueva;

    public VistaCambioClave() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700,500);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        mainPanel.setBorder(new EmptyBorder(40,60,40,60));
        String path = "src/clienteApp/images/menu.png";
        this.setIconImage(new ImageIcon(path).getImage());
        add(mainPanel);
    }
}
