package clienteApp.vista;

import clienteApp.controlador.ControladorMenuPrincipal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class VistaPrincipal extends JFrame{
    private JPanel mainContainer;
    private JButton retiro;
    private JButton clave;
    private JButton salir;


    public VistaPrincipal() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(500,350);
        addComponents();
        setLocationRelativeTo(null);
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

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Ventana Principal de Navegación", JOptionPane.INFORMATION_MESSAGE);
    }
}
