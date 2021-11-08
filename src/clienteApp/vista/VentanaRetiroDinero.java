package clienteApp.vista;

import clienteApp.controlador.controladorRetiro;

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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600,400);
        addComponents();
        setTitle("Cajero automático");
        getContentPane().add(mainContainer);
        setVisible(true);
    }

    private void addComponents() {
        saldo.setPreferredSize(new Dimension(40,15));
        monto.setPreferredSize(new Dimension(40,15));
        restante.setPreferredSize(new Dimension(40,15));
        String path = "src/clienteApp/images/atm.png";
        this.setIconImage(new ImageIcon(path).getImage());

    }

    public void salir() {
        System.exit(0);
    }

    public float getSaldo() {
        return Float.parseFloat(saldo.getText());
    }

    public float getRetiro() {
        return Float.parseFloat(monto.getText());
    }

    public void setResultado(float resultado) {
        restante.setText(String.valueOf(resultado));
    }
    public void limpiar(){
        saldo.setText("");
        restante.setText("");
        monto.setText("");
    }

    public void addListener(ActionListener al) {
        limpiarButton.addActionListener(al);
        aceptarButton.addActionListener(al);
        regresarButton.addActionListener(al);
    }


}
