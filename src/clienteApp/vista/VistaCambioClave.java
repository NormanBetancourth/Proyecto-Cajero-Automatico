package clienteApp.vista;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class VistaCambioClave extends JFrame{
    private JPanel mainPanel;
    private JPasswordField claveActual;
    private JButton limpiarButton;
    private JButton aceptarButton;
    private JButton regresarButton;
    private JPasswordField ClaveNueva;
    private JPasswordField confirmacionClaveNueva;

    private String txtClaveActual;
    private String txtNuevaClave;
    private String txtValidarNuevaClave;

    public String getTxtClaveActual() {
        actualizarClaveActual();
        return txtClaveActual;
    }

    public void addListener(ActionListener al){
        limpiarButton.addActionListener(al);
        aceptarButton.addActionListener(al);
        regresarButton.addActionListener(al);
    }

    private void actualizarClaveActual() {
        txtClaveActual = String.valueOf(claveActual.getPassword());
    }

    public String getTxtNuevaClave() {
        actualizarClaveNueva();
        return txtNuevaClave;
    }

    private void actualizarClaveNueva() {
        txtNuevaClave = String.valueOf(ClaveNueva.getPassword());
    }

    public String getTxtValidarNuevaClave() {
        actualizarValidarNuevaclave();
        return txtValidarNuevaClave;
    }

    private void actualizarValidarNuevaclave() {
        //todo: validar la contraseña
        txtValidarNuevaClave = String.valueOf(confirmacionClaveNueva.getPassword());
    }

    public VistaCambioClave() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(700,500);
        addComponents();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponents() {
        mainPanel.setBorder(new EmptyBorder(40,60,40,60));
        String path = "src/clienteApp/images/menu.png";
        this.setIconImage(new ImageIcon(path).getImage());
        add(mainPanel);
    }

    public void clearTF() {
        claveActual.setText("");
        ClaveNueva.setText("");
        confirmacionClaveNueva.setText("");
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Login", JOptionPane.INFORMATION_MESSAGE);
    }
    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Login", JOptionPane.ERROR_MESSAGE);
    }
}
