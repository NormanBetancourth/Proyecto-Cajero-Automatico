package clienteApp.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class VentanaRetiroDinero extends JFrame{
    private JPanel mainContainer;
    private JTextField saldo;
    private JButton limpiarButton;
    private JButton aceptarButton;
    private JButton regresarButton;
    private JTextField monto;
    private JTextField restante;

    public VentanaRetiroDinero() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(600,400);
        addComponents();
        setTitle("Cajero automático");
        getContentPane().add(mainContainer);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addComponents() {
        saldo.setPreferredSize(new Dimension(40,15));
        monto.setPreferredSize(new Dimension(40,15));
        restante.setPreferredSize(new Dimension(40,15));
        String path = "src/clienteApp/images/atm.png";
        this.setIconImage(new ImageIcon(path).getImage());

    }

    public String getSaldo() {
        return saldo.getText();
    }

    public void setSaldo(String saldo) {
        this.saldo.setText(saldo);
    }

    public String getMonto() {
        return monto.getText();
    }

    public void setMonto(String monto) {
        this.monto.setText(monto);
    }

    public String getRestante() {
        return restante.getText();
    }

    public void setRestante(String restante) {
        this.restante.setText(restante);
    }

    public void clearTF(){
        //saldo.setText(null);
        restante.setText(null);
        monto.setText(null);
    }

    public void addListener(ActionListener al) {
        limpiarButton.addActionListener(al);
        aceptarButton.addActionListener(al);
        regresarButton.addActionListener(al);
    }

    public void showMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Retiro de Dinero", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String message){
        JOptionPane.showMessageDialog(this, message,"Pantalla de Retiro de Dinero", JOptionPane.ERROR_MESSAGE);
    }
}
