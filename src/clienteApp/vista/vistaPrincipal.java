package clienteApp.vista;

import clienteApp.controlador.ControladorMenuPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class vistaPrincipal extends JFrame{
    private JPanel mainContainer;
    private JButton retiro;
    private JButton clave;
    private JButton salir;


    public vistaPrincipal() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,350);
        addComponents();
        setVisible(true);
    }

    public  void addListeners(ActionListener al) {
        retiro.addActionListener(al);
        clave.addActionListener(al);
        salir.addActionListener(al);
    }

    private void addComponents() {
        mainContainer.setBorder(new EmptyBorder(40,100,40,100));
        String path = "src/clienteApp/images/menu.png";
        this.setIconImage(new ImageIcon(path).getImage());
        add(mainContainer);
    }

    public void salir() {
        System.exit(0);
    }
}
