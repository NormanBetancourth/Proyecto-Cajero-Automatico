package clienteApp.vista;

import javax.print.attribute.standard.JobImpressions;
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
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600,400);
        addComponents();
        setTitle("Cajero automático");
        getContentPane().add(mainContainer);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public String getUsuario(){
        return Usuario.getText();
    }

    public String getPassword(){
        return String.valueOf(password.getPassword());
    }

    public void setUsuarioText(String texto){
        Usuario.setText(texto);
    }

    private void addComponents() {
        cancelar.setPreferredSize(new Dimension(40,15));
        aceptar.setPreferredSize(new Dimension(40,15));
        this.setIconImage(new ImageIcon("src/clienteApp/images/login.png").getImage());
    }

    public String getPasswordF() {
        return String.valueOf(password.getPassword());
    }

    public void addListener(ActionListener actionListener){
        aceptar.addActionListener(actionListener);
        cancelar.addActionListener(actionListener);
    }

    public void clearTF(){
        Usuario.setText("");
        password.setText("");
    }

    public void showMessage(String message){
        //System.out.println(message);
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
