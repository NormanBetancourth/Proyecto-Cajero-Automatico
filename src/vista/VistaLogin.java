package vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class VistaLogin extends JFrame {
    private JPanel mainContainer;
    private JTextField Usuario;
    private JButton aceptar;
    private JButton cancelar;
    private JLabel passwordLabel;
    private JLabel usuarioLabel;
    private JPasswordField password;


    public VistaLogin() throws HeadlessException {
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

    public String getPasswordF() {
        return String.valueOf(password.getPassword());
    }

    public void addListener(ActionListener actionListener){
        aceptar.addActionListener(actionListener);
        cancelar.addActionListener(actionListener);
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Login", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Login", JOptionPane.ERROR_MESSAGE);
    }

    public int yesNoMessage(String message){
        return JOptionPane.showConfirmDialog(this, message,"Pantalla de Login", JOptionPane.YES_NO_OPTION);
    }

    public void salir(){
        System.exit(0);
    }

}
