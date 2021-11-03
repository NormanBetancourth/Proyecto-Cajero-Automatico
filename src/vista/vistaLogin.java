package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class vistaLogin extends JFrame {
    private JPanel mainContainer;
    private JTextField Usuario;
    private JButton aceptar;
    private JButton cancelar;
    private JLabel passwordLabel;
    private JLabel usuarioLabel;
    private JPasswordField password;


    public vistaLogin() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500,300);
        addComponents();
        getContentPane().add(mainContainer);
        setVisible(true);
    }

    private void addComponents() {

        mainContainer.setBorder(new EmptyBorder(40,30,40,30));
        passwordLabel.setBorder(new EmptyBorder(0,15,0,0));
        usuarioLabel.setBorder(new EmptyBorder(0,15,0,0));
        cancelar.setPreferredSize(new Dimension(40,15));
        aceptar.setPreferredSize(new Dimension(40,15));

    }

}
