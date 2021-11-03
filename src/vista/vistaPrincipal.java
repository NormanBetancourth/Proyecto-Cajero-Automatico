package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class vistaPrincipal extends JFrame{
    private JPanel mainContainer;
    private JButton retiro;
    private JButton clave;
    private JButton salir;


    public vistaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,500);
        addComponents();
        setVisible(true);
    }

    private void addComponents() {
        mainContainer.setBorder(new EmptyBorder(40,60,40,60));
        add(mainContainer);
    }
}
