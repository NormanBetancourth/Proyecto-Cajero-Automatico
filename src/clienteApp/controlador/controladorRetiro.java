package clienteApp.controlador;

import clienteApp.vista.VentanaRetiroDinero;
import clienteApp.vista.VistaCambioClave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controladorRetiro {
    private VentanaRetiroDinero vista;
    private ActionCommand actionCommand;
    private ControladorPrincipal ctrl;

    public controladorRetiro(ControladorPrincipal controladorPrincipal) {
        ctrl = controladorPrincipal;
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
