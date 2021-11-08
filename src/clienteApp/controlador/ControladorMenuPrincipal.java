package clienteApp.controlador;

import clienteApp.vista.vistaPrincipal;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorMenuPrincipal {
    private vistaPrincipal vista;
    private ControladorPrincipal ctrl;

    public ControladorMenuPrincipal(ControladorPrincipal controladorPrincipal) {
        ctrl = controladorPrincipal;
    }

    public void addListeners(){
        vista.addListeners(new ActionCommand());
    }

    public void iniciar() {
        vista = new vistaPrincipal();
        addListeners();
    }

    private class ActionCommand implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()){
                case "retiro" ->{
                    ctrl.Retiro();
                    vista.setVisible(false);
                }
                case "clave" ->{
                    ctrl.cambioClave();
                    vista.setVisible(false);

                }
                case "salir" ->{
                    vista.salir();
                }

            }
        }
    }
}
