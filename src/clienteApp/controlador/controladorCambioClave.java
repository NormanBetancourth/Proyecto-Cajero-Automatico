package clienteApp.controlador;

import clienteApp.vista.VistaCambioClave;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class controladorCambioClave {
    private VistaCambioClave vista;
    private ActionCommand actionCommand;
    private ControladorPrincipal ctrl;

    public controladorCambioClave(ControladorPrincipal controladorPrincipal) {
        ctrl = controladorPrincipal;
    }

    public void iniciar() {
        vista = new VistaCambioClave();
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
