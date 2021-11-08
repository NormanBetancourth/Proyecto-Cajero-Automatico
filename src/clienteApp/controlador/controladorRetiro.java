package clienteApp.controlador;

import clienteApp.vista.VentanaRetiroDinero;
import clienteApp.vista.VistaCambioClave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controladorRetiro {
    private VentanaRetiroDinero vista;
    private ActionCommand actionCommand;
    private ControladorPrincipal ctrl;

    private float saldo;
    private float retiro;
    private float resultado;

    public controladorRetiro(ControladorPrincipal controladorPrincipal) {
        ctrl = controladorPrincipal;
    }

    public float getSaldo() {
        actualizarSaldo();
        return saldo;
    }

    private void actualizarSaldo() {
        saldo = vista.getSaldo();
    }

    public float getRetiro() {
        actualizarRetiro();
        return retiro;
    }



    private void actualizarRetiro() {
        retiro = vista.getRetiro();
    }

    public float getResultado() {
        return resultado;
    }

    public void setResultado(float resultado) {
        vista.setResultado(resultado);
    }

    public void iniciar() {
        vista = new VentanaRetiroDinero();
        vista.addListener(new ActionCommand());
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "Limpiar" -> {
                    vista.limpiar();
                }
                case "Aceptar" -> {
                    //todo: verificaciones(campos vacios, formatos, ...) y conexion con el servidor

                }
                case "Regresar" -> {
                    vista.setVisible(false);
                    ctrl.menuPrincipal();
                }

            }

        }
    }
}
